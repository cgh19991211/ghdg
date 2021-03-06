package com.gh.ghdg.api.controller.business;

import cn.hutool.core.util.StrUtil;
import com.gh.ghdg.api.utils.ApiConstants;
import com.gh.ghdg.businessMgr.Repository.BlacklistRepository;
import com.gh.ghdg.businessMgr.Repository.BloggerInfoRepository;
import com.gh.ghdg.businessMgr.Repository.BloggerRepository;
import com.gh.ghdg.businessMgr.bean.dto.ChangePasswordDto;
import com.gh.ghdg.businessMgr.bean.entities.Blacklist;
import com.gh.ghdg.businessMgr.bean.entities.Blogger;
import com.gh.ghdg.businessMgr.bean.entities.BloggerInfo;
import com.gh.ghdg.businessMgr.service.BloggerInfoService;
import com.gh.ghdg.businessMgr.service.BloggerService;
import com.gh.ghdg.common.cache.redis.RedisUtil;
import com.gh.ghdg.common.enums.Status;
import com.gh.ghdg.common.security.AccountInfo;
import com.gh.ghdg.common.security.JwtUtil;
import com.gh.ghdg.common.utils.HttpKit;
import com.gh.ghdg.common.utils.PwdEncrypt;
import com.gh.ghdg.common.utils.Result;
import com.gh.ghdg.common.utils.constant.Constants;
import io.swagger.annotations.Authorization;
import org.apache.commons.collections.map.HashedMap;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.*;

@RestController
@RequestMapping("blogger")
public class BloggerLogin {
    
    private static final String VERRIFY = "-verify-code";
    
    @Autowired
    private BloggerService bloggerService;
    
    @Autowired
    private BloggerInfoService bloggerInfoService;
    
    @Autowired
    private BloggerInfoRepository bloggerInfoRepository;
    
    @Resource
    private BloggerRepository bloggerRepository;
    
    @Resource
    private BlacklistRepository blacklistRepository;
    
    @Autowired
    private JavaMailSender javaMailSender;
    
    @Autowired
    private RedisUtil redisUtil;
    
    @Autowired
    private PwdEncrypt pwdEncrypt;
    
    @GetMapping("/verifyCode")
    public Result getVerifyCode(){
        String curBloggerId = JwtUtil.getCurBloggerId();
        Optional<BloggerInfo> optional = bloggerInfoRepository.findByBloggerId(curBloggerId);
        if(!optional.isPresent()){
            return Result.error(false,"用户不存在",null,Constants.FAILED);
        }
        BloggerInfo bloggerInfo = optional.get();
        SimpleMailMessage message = new SimpleMailMessage();
        // 设置邮件主题
        message.setSubject("你好,这是一封验证码邮件");
        // 设置邮件发送者，这个跟application.yml中设置的要一致
        message.setFrom("1036985893@qq.com");
        // 设置邮件接收者，可以有多个接收者，中间用逗号隔开，以下类似
        // message.setTo("10*****16@qq.com","12****32*qq.com");
        message.setTo(bloggerInfo.getEmail());
        //生成四位验证码,模拟验证码功能
        Random random=new Random();
        String verify = String.valueOf(random.nextInt(9000)+1000);
        // 设置邮件抄送人，可以有多个抄送人
        //message.setCc("12****32*qq.com");
        // 设置隐秘抄送人，可以有多个
        //message.setBcc("7******9@qq.com");
        // 设置邮件发送日期
        message.setSentDate(new Date());
        // 设置邮件的正文
        message.setText("你的验证码为:"+verify+",注意有效期为5分钟");
        // 发送邮件
        javaMailSender.send(message);
        
        //验证码添加进缓存
        redisUtil.set(curBloggerId+"VERRIFY",verify,300);
        return Result.suc("验证码已发送至邮箱");
    }
    
    @PostMapping("/changePwd")
    public Result changePassword(@RequestBody ChangePasswordDto c){
        String code = c.getCode();
        String newPwd = c.getPwd();
        String curBloggerId = JwtUtil.getCurBloggerId();
        String cacheCode = redisUtil.get(curBloggerId+"VERRIFY");
        if(cacheCode==null||!StrUtil.equals(code,cacheCode)) {
            return Result.error(false,"验证码错误或者已过期",null, ApiConstants.VERIFY_NOT_FOUND);
        }
        Optional<Blogger> optional = bloggerRepository.findById(curBloggerId);
        if(!optional.isPresent()){
            return Result.error(false,"未知错误，无法找到该博主",null,ApiConstants.UNKNOW_ERROR);
        }
        //密码格式验证，暂时不验证
        Blogger blogger = optional.get();
        String encriptedNewPassword = pwdEncrypt.encryptPassword(newPwd,blogger.getSalt());
        blogger.setPassword(encriptedNewPassword);
        bloggerRepository.save(blogger);
        return Result.suc("密码修改成功");
    }
    
    /**
     * 登陆成功之后给前端发送AccessToken还有RefreshToken
     * @param account
     * @param password
     * @return
     */
    @PostMapping("/login")
    public Result bloggerLogin(@RequestParam String account, @RequestParam String password){
        //TODO: 取缓存
        
        Blogger blogger = bloggerService.findByAccount(account);
        if(blogger.getStatus()==Status.失效){
            return Result.error(false,"该用户已被冻结",null,Constants.FROZEN);
        }
        Map<String,Object> data = new HashMap<>();
        if(blogger!=null){
            //TODO: 判断密码是否正确
            String dbpwd = blogger.getPassword();
            String salt = blogger.getSalt();
            String s = bloggerService.encryptPassword(password, salt);
            if(!StrUtil.equals(s,dbpwd)){
                return Result.error(false,"密码错误",null,Constants.WRONG_PASSWORD);
            }
            if(blogger.getStatus()!=Status.生效){
                Blacklist blacked = blacklistRepository.findBy_id(blogger.getId());
                if(blacked!=null)
                    return Result.error(false,"您因："+blacked.getReason()+"，被封禁，截至："+blacked.getUntil(),null,Constants.BLACKED);
            }
            //blogger login, return AccessToken,FreshToken
            String refreshToken = JwtUtil.signBloggerRefreshToken(blogger);
            String accessToken = JwtUtil.signBloggerAccessToken(blogger);
            data.put(Constants.Blogger_ACCESS_TOKEN,refreshToken);
            data.put(Constants.Blogger_REFRESH_TOKEN,accessToken);
            
            //TODO: 添加缓存
            
            return Result.suc("登陆成功",data);
        }else {
            return Result.error(false,"该账户不存在",null,Constants.FAILED);
        }
    }
    
    @PostMapping("/logout")
//    @RequiresAuthentication
    public Result bloggerLogout(){
        //TODO:缓存里删除RefreshToken
        
        return Result.suc("logout response");
    }
    
    @PostMapping("/signIn")
    public Result bloggerSignIn(@RequestParam String account, @RequestParam String password, String email){
        Map<String,Object> data = new HashMap<>();
        //注册
        Blogger newBlogger = bloggerService.signIn(account, password);
        //token的payload是博主的_id以及account
        String refreshToken = JwtUtil.signBloggerRefreshToken(newBlogger);
        String accessToken = JwtUtil.signBloggerAccessToken(newBlogger);
        data.put(Constants.Blogger_ACCESS_TOKEN,accessToken);
        data.put(Constants.Blogger_REFRESH_TOKEN,refreshToken);
        BloggerInfo bloggerInfo = new BloggerInfo();
        bloggerInfo.setBloggerId(newBlogger.get_id());
        bloggerInfo.setBloggerName(newBlogger.getAccount());//博主的bloggerName就是账号，是不可更改的
        bloggerInfoService.save(bloggerInfo);
        //TODO: 添加缓存:refreshToken
    
        //前端收到注册成功码，跳转到修改个人信息页面;或者将来使用微信扫码登陆，获取微信信息来设置个人信息
        return Result.suc("注册用户成功",data);
    }
    
    @PostMapping("/signOut")
    public Result bloggerSignOut(@RequestParam String bloggerId){
        if(StrUtil.equals(JwtUtil.getCurBloggerId(),bloggerId)){
            bloggerService.signOut(bloggerId);
            return Result.suc("sign out");
        }
        return Result.error("sign out false");
    }
    
    @GetMapping("/curBlogger")
//    @RequiresAuthentication
    public Result getCurBloggerInfo(){
//        Blogger blogger = bloggerOptional.get();
        String bloggerAccessToken = HttpKit.getBloggerAccessToken();
        /**
         * 验证token是否过期
         */
        if(StrUtil.isBlank(bloggerAccessToken)||JwtUtil.isExpiredToken(bloggerAccessToken)){
//            return Result.suc("未登录或登陆过期了",null,Constants.ACCESS_TOKEN_EXPIRE_CODE);
            return Result.suc("登陆过期了");
        }
//        JwtUtil.verify(bloggerAccessToken,blogger.getAccount(),blogger.getPassword());
        String curBloggerId = JwtUtil.getCurBloggerId();
        if(StrUtil.isBlank(curBloggerId)){
            return Result.error(false,"未登录",null,Constants.NOT_LOGIN);
        }
        Optional<Blogger> bloggerOptional = bloggerRepository.findById(curBloggerId);
        if(!bloggerOptional.isPresent()){
            return Result.error(false,"用户未注册",null,Constants.USER_NOT_FOUND);
        }
        
        return Result.suc(bloggerInfoService.findByBloggerId(curBloggerId));
    }
    
    @GetMapping("/refreshToken")
    public Result refreshToken(String BloggerRefreshToken){
        //验证刷新token
        if(JwtUtil.isExpiredToken(BloggerRefreshToken)){
            return Result.suc("刷新token过期了",null,Constants.REFRESH_TOKEN_EXPIRE_CODE);
        }
        
        AccountInfo bloggerAccount = JwtUtil.getBloggerAccount(BloggerRefreshToken);
        if(bloggerAccount==null){
            Result.error(false,"无效的token",null,Constants.INVALID_TOKEN_CODE);
        }
        String bloggerId = bloggerAccount.getId();
        Optional<Blogger> bloggerOptional = bloggerRepository.findById(bloggerId);
        if(!bloggerOptional.isPresent()){
            Result.error(false,"用户不存在",null,Constants.USER_NOT_FOUND);
        }
        Blogger blogger = bloggerOptional.get();
        //刷新token没问题，接下来重新签发token
        Map<String,String> data = new HashMap<>();
        data.put(Constants.Blogger_ACCESS_TOKEN,JwtUtil.signBloggerAccessToken(blogger));
        //判断是否需要更新RefreshToken RefreshToken>2*AccessToken
        //但是这里还没有添加缓存。。先直接更新
        data.put(Constants.Blogger_REFRESH_TOKEN,JwtUtil.signBloggerAccessToken(blogger));
    
        return Result.suc(data);
    }
}
