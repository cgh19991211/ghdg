package com.gh.ghdg.common.utils.constant;

public class Constants {
    private Constants(){}
    
    //token
    public static String SYSTEM_USER_ID="-1";
    public static String ACCESS_TOKEN_NAME ="AccessToken";
    public static String REFRESH_TOKEN_NAME ="RefreshToken";
    public static String REFRESH_TOKEN_START_TIME = "RefreshTokenStartTime";
    public static String TOKEN = "token";
    public static String TOKEN_TYPE = "type";
    // 过期时间30分钟
    public static final long ACCESS_EXPIRE_TIME = 300 * 60 * 60 * 1000;//300小时
    //真实过期时间3倍access token过期时间
    public static final long REFRESH_EXPIRE_TIME = ACCESS_EXPIRE_TIME*2;//ACCESS_EXPIRE_TIME*3

    public static String FIRST_LOGIN = "FirstLogin";

    public static String VERIFYY_CODE = "VerifyCode";
    public static String VERIFYY_CODE_ATTEMPTS = "VerifyCodeAttempts";
//
//    String RETRIVE_PWD_VERIFY_CODE = "RetrivePwdVerifyCode";
//    String RETRIVE_PWD_VERIFY_CODE_TIME = "RetrivePwdVerifyCodeTime";

    public static String CUR_USER = "User";
    public static String CUR_MENUS = "Menus";
    public static String CUR_MENU_ROOTS = "MenuRoots";
    public static String CUR_AUTH_INFO_MAP = "AuthInfoMap";
    public static String CUR_AUTH_INFO = "AuthInfo";

    public static String CUR_APP_MENUS = "AppMenus";
    public static String CUR_APP_MENU_ROOTS = "AppMenuRoots";
    
    //错误码
    public static int SUCCESS = 200;
    public static int FAILED = 999;
    public static int ACCESS_TOKEN_EXPIRE_CODE = 50014;
    public static int REFRESH_TOKEN_EXPIRE_CODE = 50015;
    public static int INVALID_TOKEN_CODE = 50000;

}