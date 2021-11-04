package com.gh.ghdg.common.utils.exception;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

/**
 * 自定义错误信息
 */
@Component
public class MyErrorAttribute extends DefaultErrorAttributes {
	
	public static final String MESSAGE = "message";
	
	@Override
	public Map<String, Object> getErrorAttributes(WebRequest webRequest, ErrorAttributeOptions options) {
		Map<String, Object> map = super.getErrorAttributes(webRequest, options);
		
		// 设置传递自己定义的错误信息
		map.put("success", false);
		Object message = webRequest.getAttribute(MESSAGE, RequestAttributes.SCOPE_REQUEST);
		map.put(MESSAGE, message);
		return map;
	}
	
}
