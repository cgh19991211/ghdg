package com.gh.ghdg.sysMgr.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.gh.ghdg.common.utils.constant.Constants;
import com.gh.ghdg.sysMgr.bean.entities.system.User;
import com.gh.ghdg.common.utils.HttpKit;

import java.util.Date;

import static com.gh.ghdg.common.utils.constant.Constants.ACCESS_EXPIRE_TIME;

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
    public static void verify(String token, String username, String secret) throws Exception{
        Algorithm algorithm = Algorithm.HMAC256(secret);
        JWTVerifier verifier = JWT.require(algorithm)
                .withClaim("username", username)
                .build();
        DecodedJWT jwt = verifier.verify(token);
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

    public static String getUserId(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("userId").asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    /**
     * 生成token,5min后过期
     *
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
    
    /**
     * 生成刷新token
     * @param user
     * @param token
     * @param refreshDate
     * @return
     */
    public static String signRereshToken(User user){
        return sign(user, Constants.REFRESH_EXPIRE_TIME);
    }
    
    /**
     * 生成AccountToken
     * 只管返回token。
     * 在刷新token的接口中，两个新token再存入map，再把map返回给前端就行。
     * @param token
     * @param date
     * @return
     */
    public static String signAccessToken(User user){
        return sign(user,Constants.ACCESS_EXPIRE_TIME);
    }
    
}
