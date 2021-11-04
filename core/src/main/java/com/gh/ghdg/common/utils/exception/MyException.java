package com.gh.ghdg.common.utils.exception;

/**
 * 运行期的自定义异常类
 */
public class MyException extends RuntimeException {

    public MyException() {

    }

    public MyException(String message) {
        super(message);
    }

    public MyException(String message, Throwable cause) {
        super(message, cause);
    }

}
