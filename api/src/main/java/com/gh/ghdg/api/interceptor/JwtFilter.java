package com.gh.ghdg.api.interceptor;

import com.gh.ghdg.sysMgr.security.JwtToken;
import com.gh.ghdg.common.utils.constant.Constants;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 拦截请求
 */
public class JwtFilter extends BasicHttpAuthenticationFilter {

    /**
     * 判断用户是否想要登入。
     * 检测header里面是否包含Authorization字段即可
     */
    @Override
    protected boolean isLoginAttempt(ServletRequest request, ServletResponse response) {
        HttpServletRequest req = (HttpServletRequest) request;
        String token = req.getHeader(Constants.ACCESS_TOKEN_NAME);
        return token != null;
    }

    /**
     *登录验证
     */
    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String authorization = httpServletRequest.getHeader(Constants.ACCESS_TOKEN_NAME);

        JwtToken token = new JwtToken(authorization);
        // 提交给realm进行登入，如果错误他会抛出异常并被捕获
        getSubject(request, response).login(token);
        // 如果没有抛出异常则代表登入成功，返回true
        return true;
    }

    /**
     * 这里我们详细说明下为什么最终返回的都是true，即允许访问
     * 例如我们提供一个地址 GET /article
     * 登入用户和游客看到的内容是不同的
     * 如果在这里返回了false，请求会被直接拦截，用户看不到任何东西
     * 所以我们在这里返回true，Controller中可以通过 subject.isAuthenticated() 来判断用户是否登入
     * 如果有些资源只有登入用户才能访问，我们只需要在方法上面加上 @RequiresAuthentication 注解即可
     * 但是这样做有一个缺点，就是不能够对GET,POST等请求进行分别过滤鉴权(因为我们重写了官方的方法)，但实际上对应用影响不大
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        if (isLoginAttempt(request, response)) {//有token，则进来进行token验证，并授权
            try {
                /**
                 * 非登录请求，
                 * jwtFilter执行executeLogin方法进入自定义realm进行认证
                 * doGetAuthenticationInfo（认证不通过，抛出异常，异常的处理稍后详细说明）
                 * 和授权doGetAuthorizationInfo，
                 * 都通过然后进入自己的控制器；
                 * 在这里是无论通不通过都进入控制器，因为除了登陆用户还有游客
                 * 但是验证不通过的话没有权限，在设置了权限控制的控制器中就无法访问。
                 * 对于博客业务，游客可以访问的就不进行权限控制，若是游客不能访问的则设置@RequiresAuthentication
                 */
                executeLogin(request, response);
            } catch (Exception e) {//无token，设置Response状态为401，但仍然允许访问该请求所访问的controller，但是没有授权，controller能不能通过就是另外说了
                response401(request, response);
                return true;
            }
        }
        return true;
    }

    /**
     * 对跨域提供支持
     */
    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        //如果前端请求需要带有cookies等信息的话，需要开启Access-Control-Allow-Credentials
        httpServletResponse.setHeader("Access-Control-Allow-Credentials","true");
        httpServletResponse.setHeader("Access-control-Allow-Origin", httpServletRequest.getHeader("Origin"));
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", httpServletRequest.getHeader("Access-Control-Request-Headers"));
//        response.setHeader("Access-Control-Expose-Headers","Cache-Control,Content-Type,Expires,Pragma,Content-Language,Last-Modified,token");
//        response.setHeader("token", JwtToken.createToken(user.getId())); //设置响应头
    
        // 跨域时(既发送非简单请求时)会首先发送一个option请求(预检请求)，这里我们给option请求直接返回正常状态(告诉浏览器允许请求)
        if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
            httpServletResponse.setStatus(HttpStatus.OK.value());
            return true;
        }
        return super.preHandle(request, response);
    }

    /**
     * 将非法请求跳转到 /401
     */
    private void response401(ServletRequest req, ServletResponse resp) {
        try {
            HttpServletResponse httpServletResponse = (HttpServletResponse) resp;
//            httpServletResponse.sendRedirect("/401");
            httpServletResponse.setStatus(401);
        } catch (Exception e) {
            Logger logger = LoggerFactory.getLogger(JwtFilter.class);
            logger.error(e.getMessage());
        }
    }
}
