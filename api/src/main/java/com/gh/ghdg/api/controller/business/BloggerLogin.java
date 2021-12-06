package com.gh.ghdg.api.controller.business;

import cn.hutool.core.util.StrUtil;
import com.gh.ghdg.businessMgr.bean.entities.Blogger;
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
    
    /**
     * 登陆成功之后给前端发送AccessToken还有RefreshToken
     * @param account
     * @param password
     * @return
     */
    @PostMapping("/login")
    public Result bloggerLogin(@RequestBody String account, @RequestBody String password){
        //TODO: 取缓存
        
        Blogger blogger = bloggerService.findByAccount(account);
        Map<String,Object> data = new HashMap<>();
//        String accessToken = "";
//        String refreshToken = "";
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
            data.put(Constants.ACCESS_TOKEN_NAME,refreshToken);
            data.put(Constants.REFRESH_TOKEN_NAME,accessToken);
            
            //TODO: 添加缓存
            
            return Result.suc("登陆成功",data);
        }else{//传进来的账号不存在
            //TODO:判断是新建账户还是账号错误，加入手机验证码||邮箱之后，以手机验证码|邮箱（是否已使用）来判断是否是新增
            
            
            //注册
            Blogger newBlogger = bloggerService.signIn(account, password);
            String refreshToken = JwtUtil.signBloggerRefreshToken(newBlogger);
            String accessToken = JwtUtil.signBloggerAccessToken(newBlogger);
            data.put(Constants.ACCESS_TOKEN_NAME,accessToken);
            data.put(Constants.REFRESH_TOKEN_NAME,refreshToken);
            
            //TODO: 添加缓存:refreshToken
            
            //前端收到注册成功码，跳转到修改个人信息页面;或者将来使用微信扫码登陆，获取微信信息来设置个人信息
            return Result.suc("注册用户成功",data, Constants.SIGN_IN_SUC);
        }
    }
    
    @GetMapping("/logout")
//    @RequiresAuthentication
    public Result bloggerLogout(){
        //TODO:缓存里删除RefreshToken
        
        return null;
    }
    
    @PostMapping("/curBlogger")
//    @RequiresAuthentication
    public Result getCurBloggerInfo(){
        String curBloggerId = JwtUtil.getCurBloggerId();
        //TODO:根据id获取bloggerInfo
        
        return null;
    }
}
