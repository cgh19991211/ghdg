package com.gh.ghdg.api.interceptor;

import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.gh.ghdg.common.utils.Result;
import com.gh.ghdg.common.utils.constant.Constants;
import com.gh.ghdg.common.utils.exception.MyErrorAttribute;
import com.gh.ghdg.common.utils.exception.MyException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.persistence.RollbackException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
@ResponseBody
public class MyExceptionHandler {

	/**
	 * 自定义异常处理：适配页面和Ajax请求
	 * @param req
	 * @return
	 */
	@ExceptionHandler(value = Exception.class)
	public String exceptionHandler(HttpServletRequest req, Exception e) {
		e.printStackTrace();
		return requestForward(req);
    }

    /**
     * 验证异常
     * @param req
     * @param e
     * @return
     */
    @ExceptionHandler(value = AuthenticationException.class)
    public String exceptionHandler(HttpServletRequest req, AuthenticationException e) {
		e.printStackTrace();
		String msg;
		if (e instanceof UnknownAccountException) {
			msg = "用户不存在";
		} else if (e instanceof DisabledAccountException) {
			msg = "用户失效";
		} else if (e instanceof IncorrectCredentialsException) {
			msg = "密码错误";
		} else {
			msg = e.getMessage();
		}
		req.setAttribute(MyErrorAttribute.MESSAGE, msg);
		return requestForward(req);
	}
	
	@ExceptionHandler(value = UnauthenticatedException.class)
	public String exceptionHandler(HttpServletRequest req, UnauthenticatedException e){
    	e.printStackTrace();
    	req.setAttribute(MyErrorAttribute.MESSAGE,"用户未授权");
    	return requestForward(req);
	}

	/**
	 * 授权异常
	 * @param res
	 * @param e
	 */
	@ExceptionHandler(value = UnauthorizedException.class)
	public void exceptionHandler(HttpServletResponse res, UnauthorizedException e) throws IOException {
		e.printStackTrace();
		res.sendError(403);
	}

	/**
	 * 错误的请求地址
	 * @param res
	 * @param e
	 */
	@ExceptionHandler(value = NoHandlerFoundException.class)
	public void exceptionHandler(HttpServletResponse res, NoHandlerFoundException e) throws IOException {
		e.printStackTrace();
		res.sendError(404);
	}

	/**
	 * 实体校验错误处理
	 * @param req
	 * @param e
	 * @return
	 */
	@ExceptionHandler(value = ConstraintViolationException.class)
	public String exceptionHandler(HttpServletRequest req, ConstraintViolationException e) {
		e.printStackTrace();

		List<String> msgs = new ArrayList<>();
		for (ConstraintViolation cv : e.getConstraintViolations()) {
			msgs.add(cv.getMessage());
		}

		boolean is400 = !msgs.isEmpty();
		if (is400) {
			String msg = StrUtil.join("，",msgs);//逗号是中文的
			req.setAttribute(MyErrorAttribute.MESSAGE, msg);
		}

		return requestForward(req, is400 ? 400 : 500);
	}

	@ExceptionHandler(value = TransactionSystemException.class)
	public String exceptionHandler(HttpServletRequest req, TransactionSystemException e) {
		e.printStackTrace();

		List<String> errMsgs = getErrMsgs(e);
		boolean is400 = !errMsgs.isEmpty();
		if (is400) {
			req.setAttribute(MyErrorAttribute.MESSAGE, errMsgs);
		}

		return requestForward(req, is400 ? 400 : 500);
	}
	
	
	/**
	 * 取到实体校验错误
	 * @param e
	 * @return
	 */
	private List<String> getErrMsgs(TransactionSystemException e) {
		e.printStackTrace();

		List<String> errMsgs = new ArrayList<>();
		if (e.getCause() instanceof  RollbackException) {
			RollbackException re = (RollbackException) e.getCause();
			if (re.getCause() instanceof ConstraintViolationException) {
				ConstraintViolationException cve = (ConstraintViolationException) re.getCause();
				for (ConstraintViolation cv : cve.getConstraintViolations()) {
					errMsgs.add(cv.getMessage());
				}
			}
		}
		return errMsgs;
	}


	private final String FORWARD = "forward:/error";

	private String requestForward(HttpServletRequest req) {
		return requestForward(req, 500);
	}

	private String requestForward(HttpServletRequest req, int code) {
		// 生产环境不抛具体错误
//	    if(ContextHelper.isProd() && code == 500) {
//			req.setAttribute(MyErrorAttribute.MESSAGE, "服务器内部错误");
//		}
		req.setAttribute("javax.servlet.error.status_code", code);
		return FORWARD;
	}
	

	
}
