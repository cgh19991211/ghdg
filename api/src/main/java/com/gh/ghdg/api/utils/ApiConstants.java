package com.gh.ghdg.api.utils;

public class ApiConstants {
    public static  final String HTTP_METHOD_OPTIONS="OPTIONS";
    public static  final String ADMIN_ACCOUNT="admin";

    public static  final String IP_UNKNOW="unknown";
    public static  final String IPV6_LOCALHOST="0:0:0:0:0:0:0:1";
    public static  final String IPV4_LOCALHOST="127.0.0.1";

    public static final int VERIFY_NOT_FOUND = 998;//验证码错误
    public static final int VERIFY_EXPIRED = 997;//验证码过期，过期与错误当做同一种错误处理，只用998
    
    public static final int UNKNOW_ERROR = 9998;
}
