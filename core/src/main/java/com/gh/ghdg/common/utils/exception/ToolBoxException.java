package com.gh.ghdg.common.utils.exception;


import cn.hutool.core.util.StrUtil;

/**
 * 工具类初始化异常
 */
public class ToolBoxException extends RuntimeException{
	private static final long serialVersionUID = 8247610319171014183L;

	public ToolBoxException(Throwable e) {
		super(e.getMessage(), e);
	}

	public ToolBoxException(String message) {
		super(message);
	}

	public ToolBoxException(String messageTemplate, Object... params) {
		super(StrUtil.format(messageTemplate, params));
	}

	public ToolBoxException(String message, Throwable throwable) {
		super(message, throwable);
	}

	public ToolBoxException(Throwable throwable, String messageTemplate, Object... params) {
		super(StrUtil.format(messageTemplate, params), throwable);
	}
}
