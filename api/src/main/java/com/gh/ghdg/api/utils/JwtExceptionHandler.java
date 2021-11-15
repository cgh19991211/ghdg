package com.gh.ghdg.api.utils;

import com.gh.ghdg.common.utils.Result;
import com.gh.ghdg.common.utils.constant.Constants;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.security.SignatureException;
/**
 * Jwt的异常被转发到这儿，再重新抛出以便全局异常处理器捕获
 */
@RestController
public class JwtExceptionHandler {

//    @RequestMapping("/accessTokenException")
//    public void expiredJwtException(HttpServletRequest request) {
//
//        throw ((TokenExpiredException) request.getAttribute("accessTokenException"));
//    }
    
    @RequestMapping("/accessTokenException")
    public Result expiredJwtException(HttpServletRequest request) {
        return Result.error(false,"access token已过期",null, Constants.ACCESS_TOKEN_EXPIRE_CODE);
    }
    
    @RequestMapping("/refreshTokenException")
    public Result expiredRefreshTokenException(HttpServletRequest request) {
        return Result.error(false,"refresh token已过期",null, Constants.REFRESH_TOKEN_EXPIRE_CODE);
    }
    
    
}
