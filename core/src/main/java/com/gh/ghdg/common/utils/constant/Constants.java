package com.gh.ghdg.common.utils.constant;

public class Constants {
    private Constants(){}
    
    //token
    public static String SYSTEM_USER_ID="-1";
    public static String ACCESS_TOKEN_NAME ="AccessToken";
    public static String REFRESH_TOKEN_NAME ="RefreshToken";
    public static String Blogger_ACCESS_TOKEN = "BloggerAccessToken";
    public static String Blogger_REFRESH_TOKEN = "BloggerRefreshToken";
    public static String REFRESH_TOKEN_START_TIME = "RefreshTokenStartTime";
    public static String TOKEN = "token";
    public static String TOKEN_TYPE = "type";
    // 过期时间30分钟
    public static final long ACCESS_EXPIRE_TIME =30 * 60 * 1000;//30mins
    //真实过期时间3倍access token过期时间
    public static final long REFRESH_EXPIRE_TIME = ACCESS_EXPIRE_TIME*2;

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
    
    //成功码
    public static int SIGN_IN_SUC = 2021;
    
    //错误码
    public static int SUCCESS = 200;//成功
    public static int FAILED = 999;//失败
    public static int NOT_LOGIN = 4001;
    public static int WITHOUT_PERMISSION = 403;
    public static int USER_NOT_FOUND = 1024;//用户不存在
    public static int BLACKED = 444;
    public static int WRONG_PASSWORD = 1025;//密码错误
    public static int ACCESS_TOKEN_EXPIRE_CODE = 50014;//请求token过期
    public static int REFRESH_TOKEN_EXPIRE_CODE = 50015;//刷新token过期
    public static int INVALID_TOKEN_CODE = 50000;//无效token

}
