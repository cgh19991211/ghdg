package com.gh.ghdg.api.controller.system;

import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.gh.ghdg.common.cache.redis.RedisUtil;
import com.gh.ghdg.common.utils.HttpKit;
import com.gh.ghdg.common.utils.Result;
import com.gh.ghdg.common.utils.constant.Constants;
import com.gh.ghdg.sysMgr.bean.entities.system.User;
import com.gh.ghdg.sysMgr.core.service.system.UserService;
import com.gh.ghdg.common.security.JwtUtil;
import com.gh.ghdg.common.security.ShiroFactory;
import com.gh.ghdg.common.security.ShiroUser;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static cn.hutool.core.map.MapUtil.newHashMap;

//@Api(tags = "登陆控制器")
//@Tag(name = "登陆控制器")
@RestController
public class LoginController {
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private RedisUtil cache;
    
    @Autowired
    @Qualifier("handlerExceptionResolver")
    private HandlerExceptionResolver resolver;
    
    /**
     * 用户登录<br>
     * 1，验证没有注册<br>
     * 2，验证密码错误<br>
     * 3，登录成功
     *                              ==4. 把token添加进缓存==
     */
//    @Operation(summary = "登陆接口方法")
//    @Parameter(name = "用户信息入参")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Result login(User userF) throws Exception {
        //TODO:先查缓存看是否已经登陆
        String s = (String) cache.get(HttpKit.getAccessToken());
        if(StrUtil.isNotEmpty(s)){
            Map<String, String> result = new HashMap<>(1);
            result.put(Constants.ACCESS_TOKEN_NAME, s);
            result.put(Constants.REFRESH_TOKEN_NAME,HttpKit.getRefreshToken());
            return Result.suc(result);
        }
    
        String userName = userF.getUsername();
        String userPassword = userF.getPassword();
        String token = null;
        String refreshToken = null;
        //1,
        User user = userService.findByUsername(userName);
        if (user == null) {
            return Result.error(false,"该用户不存在",null,Constants.USER_NOT_FOUND);
        }
        String passwordHashed = userService.encryptPassword(userPassword, user.getSalt());
        //2,
        if (!user.getPassword().equals(passwordHashed)) {
            return Result.error(false,"输入的密码错误",null,Constants.WRONG_PASSWORD);
        }
        //更新登陆时间
        user.setLastLoginDate(new Date());
        //签发登陆token
        token = JwtUtil.signAccessToken(user);
        //签发刷新token
        refreshToken = JwtUtil.signRefreshToken(user);
        //设置token
        Map<String, String> result = new HashMap<>(1);
        result.put(Constants.ACCESS_TOKEN_NAME, token);
        result.put(Constants.REFRESH_TOKEN_NAME,refreshToken);
        //RefreshToken创建的时间
        cache.set(Constants.REFRESH_TOKEN_START_TIME+userName,System.currentTimeMillis(),Constants.REFRESH_EXPIRE_TIME);
        //token与user作为键值对存入缓存
        cache.set(token,user);
//        cache.set(refreshToken,user);
        //更新登陆时间
        userService.update(user);
        return Result.suc(result);
    }
    
    /**
     * 退出登陆，服务器在缓存中清除token，与token为键的用户信息缓存
     * 客户端收到退出成功的Response之后也清除本地的token:AccessToken,RefreshToken
     * @param user
     * @return
     */
//    @Operation(summary = "登出接口方法")
    @GetMapping("/logout")
    @RequiresAuthentication
    public Result logout(){
        User curUser = JwtUtil.getCurUser();
        if(curUser==null){
            return Result.error("no such user");
        }
        cache.del(Constants.REFRESH_TOKEN_START_TIME+curUser.getUsername(),
                HttpKit.getAccessToken());
//                HttpKit.getRefreshToken());
        return Result.suc("成功退出登陆");
    }
    
    /**
     * 更新token,AccessToken过期后请求这个接口
     *
     * @param refreshToken
     * @return
     */
//    @Operation(summary = "刷新token")
//    @Parameter(description = "需要RefreshToken作为入参，以判断是否需要重新登陆")
    @GetMapping("/refreshToken")
    public Result accessTokenRefresh(@RequestParam(value = "refreshToken",required = false) String refreshToken){
        if(StrUtil.isEmpty(refreshToken)){
            return Result.error(false,"invalid token",null,Constants.INVALID_TOKEN_CODE);
        }
        
        DecodedJWT decode = JWT.decode(refreshToken);
        User user = userService.findByUsername(decode.getClaim("username").asString());
        //TODO:生成新的access token
            //TODO: 判断refresh token是否过期 有效
        if(user!=null){
            JwtUtil.verifyRefreshToken(refreshToken,user.getUsername(),user.getPassword());
        }else{
            return Result.error(false,"用户不存在",null,Constants.INVALID_TOKEN_CODE);
        }

        String accessToken =JwtUtil.signAccessToken(user);
        
        //TODO: 判断是否需要更新refresh token  RefreshToken>=2*AccessToken
        //下面判断是否刷新 refreshToken，如果refreshToken 快过期了 需要重新生成一个替换掉
        //refreshToken 有效时长是应该为accessToken有效时长的2倍
        long minTimeOfRefreshToken = 2*Constants.ACCESS_EXPIRE_TIME;
        //refreshToken创建的起始时间点
        Long refreshTokenStartTime = cache.get(Constants.REFRESH_TOKEN_START_TIME+user.getUsername());
        //(refreshToken上次创建的时间点 + refreshToken的有效时长 - 当前时间点) 表示refreshToken还剩余的有效时长，
        // 如果小于2倍accessToken时长 ，则刷新 refreshToken
        if(refreshTokenStartTime == null || (refreshTokenStartTime + Constants.REFRESH_EXPIRE_TIME *1000) - System.currentTimeMillis() <= minTimeOfRefreshToken*1000){
            //刷新refreshToken
            refreshToken = JwtUtil.signRefreshToken(user);
            cache.set(Constants.REFRESH_TOKEN_START_TIME+user.getUsername(),System.currentTimeMillis(),(int)Constants.REFRESH_EXPIRE_TIME);
        }
        
        Map<String, String> map = new HashMap<>();
        map.put(Constants.ACCESS_TOKEN_NAME,accessToken);
        map.put(Constants.REFRESH_TOKEN_NAME,refreshToken);
        return Result.suc(map);
    }
    
    
    /**
     * 需要返回用户信息，包括基本信息profile，角色，权限，菜单等
     * 返回的信息给前端做权限控制 -- 在前端的store user.js 里
     * @return
     */
    @GetMapping("/curUser")
    public Result curUser() {
        User curUser = JwtUtil.getCurUser();
        //TODO: 根据该用户获取角色，菜单，权限资源路径，全部返回给前端，还有用户profile。
        ShiroUser shiroUser = ShiroFactory.me().shiroUser(curUser);
        return Result.suc(shiroUser);
    }
    
}
