package com.gh.ghdg.api.controller.business;

import cn.hutool.core.util.StrUtil;
import com.gh.ghdg.businessMgr.bean.entities.Blogger;
import com.gh.ghdg.businessMgr.bean.entities.BloggerInfo;
import com.gh.ghdg.businessMgr.service.BloggerInfoService;
import com.gh.ghdg.businessMgr.service.BloggerService;
import com.gh.ghdg.common.security.JwtUtil;
import com.gh.ghdg.common.utils.HttpKit;
import com.gh.ghdg.common.utils.Result;
import com.gh.ghdg.common.utils.constant.Constants;
import io.swagger.annotations.Authorization;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("blogger/login")
public class BloggerLogin {
    
    @Autowired
    private BloggerService bloggerService;
    
    @Autowired
    private BloggerInfoService bloggerInfoService;
    
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
        Map<String,Object> data = new HashMap<>();
        if(blogger!=null){
            //TODO: 判断密码是否正确
            String dbpwd = blogger.getPassword();
            String salt = blogger.getSalt();
            String s = bloggerService.encryptPassword(dbpwd, salt);
            if(!StrUtil.equals(s,dbpwd)){
                return Result.error(false,"密码错误",null,Constants.WRONG_PASSWORD);
            }
    
            //TODO: blogger login, return AccessToken,FreshToken
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
        
        return null;
    }
    
    @PostMapping("/signIn")
    public Result bloggerSignIn(@RequestParam String account, @RequestParam String password){
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
        bloggerInfo.setBloggerName(newBlogger.getAccount());
        bloggerInfoService.save(bloggerInfo);
        //TODO: 添加缓存:refreshToken
    
        //前端收到注册成功码，跳转到修改个人信息页面;或者将来使用微信扫码登陆，获取微信信息来设置个人信息
        return Result.suc("注册用户成功",data, Constants.SIGN_IN_SUC);
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
        String curBloggerId = JwtUtil.getCurBloggerId();
        if(StrUtil.isEmpty(curBloggerId)){
            return Result.suc("当前用户未登录");
        }
        return Result.suc(bloggerInfoService.findById(curBloggerId));
    }
}
