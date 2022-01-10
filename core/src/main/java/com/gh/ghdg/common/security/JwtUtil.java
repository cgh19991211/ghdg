package com.gh.ghdg.common.security;

import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.gh.ghdg.businessMgr.bean.entities.Blogger;
import com.gh.ghdg.common.cache.redis.RedisUtil;
import com.gh.ghdg.common.utils.HttpKit;
import com.gh.ghdg.common.utils.SpringContextUtil;
import com.gh.ghdg.common.utils.constant.Constants;
import com.gh.ghdg.sysMgr.bean.entities.system.User;
import com.gh.ghdg.sysMgr.core.dao.system.UserDao;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;
import java.util.Optional;

/**
 * @author ：enilu
 * @date ：Created in 2019/7/30 22:56
 */
public class JwtUtil {

    /**
     * 校验token是否正确
     *
     * @param token  密钥
     * @param secret 用户的密码
     * @return 是否正确
     */
    public static void verify(String token, String username, String secret){
        Algorithm algorithm = Algorithm.HMAC256(secret);
        JWTVerifier verifier = JWT.require(algorithm)
                .withClaim("username", username)
                .build();
        try {
            verifier.verify(token);
        } catch (TokenExpiredException e) {
            //TODO: 转发TokenExpiredException到TokenExpiredController
            HttpServletRequest request = HttpKit.getRequest();
            request.setAttribute("accessTokenException",e);
            try {
                request.getRequestDispatcher("/accessTokenException").forward(request,HttpKit.getResponse());
            } catch (ServletException servletException) {
                servletException.printStackTrace();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }catch (JWTVerificationException e){
            System.out.println("Jwt解码出错");
            e.printStackTrace();
        }
    }
    
    public static void verifyRefreshToken(String token, String username, String secret) throws JWTVerificationException{
        Algorithm algorithm = Algorithm.HMAC256(secret);
        JWTVerifier verifier = JWT.require(algorithm)
                .withClaim("username", username)
                .build();
        try{
            verifier.verify(token);
        }catch (TokenExpiredException e){
            //TODO: 转发TokenExpiredException到TokenExpiredController
            HttpServletRequest request = HttpKit.getRequest();
            request.setAttribute("refreshTokenException",e);
            try {
                request.getRequestDispatcher("/refreshTokenException").forward(request,HttpKit.getResponse());
            } catch (ServletException servletException) {
                servletException.printStackTrace();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }catch (JWTVerificationException e){
            System.out.println("Jwt解码出错");
            e.printStackTrace();
        }
    }

    /**
     * 获得token中的信息无需secret解密也能获得
     *
     * @return token中包含的用户名
     */
    public static String getUsername(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("username").asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    /**
     * 获取token中的账号信息
     *
     * @param token
     * @return
     */
    public static AccountInfo getAccountInfo(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            String username = jwt.getClaim("username").asString();
            String userId = jwt.getClaim("userId").asString();
            return new AccountInfo(userId, username);
        } catch (JWTDecodeException e) {
            return null;
        }
    }
    public static AccountInfo getAccountInfo(   ) {
       return getAccountInfo(HttpKit.getAccessToken());
    }

    public static String getUserId() {
        return getUserId(HttpKit.getAccessToken());
    }
    
    /**
     * 解码token获取用户信息
     * @param token
     * @return
     */
    public static String getUserId(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("userId").asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }
    
    /**
     * 1. 通过http获取token，根据token获取当前用户信息
     * 2. 获取spring上下文，从上下文中取UserService
     * 3. 根据用户信息使用UserService获取当前用户持久化对象的实例(瞬时对象)
     * @return 返回当前用户实例
     */
    public static User getCurUser(){
        String userId = getUserId();
        if(StrUtil.isEmpty(userId)){
            return null;
        }
        UserDao userDao = SpringContextUtil.getBean(UserDao.class);
        Optional<User> byId = userDao.findById(userId);
        return byId.get();
    }
    
    public static String getCurBloggerId(){
        String accessToken = HttpKit.getBloggerAccessToken();
        if(StrUtil.isEmpty(accessToken)){
            return null;
        }
        String id = "";
        try {
            DecodedJWT jwt = JWT.decode(accessToken);
            id = jwt.getClaim("_id").asString();
        } catch (JWTDecodeException e) {
            return null;
        }
        return id;
    }
    
    /**
     * 根据token从缓存中获取对应用户信息
     * @param token
     * @return
     */
    public static User getCurUser(String token){
        RedisUtil cache = SpringContextUtil.getBean(RedisUtil.class);
        Object o = cache.get(token);
        return (o!=null||o instanceof User)?(User)o:null;
    }

    /**
     * @param user 用户
     * @return 加密的token
     */
    public static String sign(User user, long expirationTime) {

            Date date = new Date(System.currentTimeMillis() + expirationTime);
            Algorithm algorithm = Algorithm.HMAC256(user.getPassword());
            // 附带username信息
            return JWT.create()
                    .withClaim("username", user.getUsername())
                    .withClaim("userId", user.getId())
                    .withExpiresAt(date)
                    .sign(algorithm);
    }
    
    public static String signBloggerToken(Blogger blogger, long expirationTime){
        Date date = new Date(System.currentTimeMillis() + expirationTime);
        Algorithm algorithm = Algorithm.HMAC256(blogger.getPassword());
        return JWT.create()
                .withClaim("account",blogger.getAccount())
                .withClaim("_id",blogger.get_id())
                .sign(algorithm);
    }
    
    /**
     * 生成刷新token
     * @param user
     * @param
     * @param
     * @return
     */
    public static String signRereshToken(User user){
        return sign(user, Constants.REFRESH_EXPIRE_TIME);
    }
    
    public static String signBloggerRefreshToken(Blogger blogger){
        return signBloggerToken(blogger,Constants.REFRESH_EXPIRE_TIME);
    }
    
    /**
     * 生成AccountToken
     * 只管返回token。
     * 在刷新token的接口中，两个新token再存入map，再把map返回给前端就行。
     * @param
     * @param
     * @return
     */
    public static String signAccessToken(User user){
        return sign(user,Constants.ACCESS_EXPIRE_TIME);
    }
    
    public static String signBloggerAccessToken(Blogger blogger){
        return signBloggerToken(blogger,Constants.ACCESS_EXPIRE_TIME);
    }
}
