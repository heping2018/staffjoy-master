package com.example.commonlib.commonlib.auth;

/**
 * 权限常量
 */
public class AuthConstant {
    public static  final String COOKIE_NAME = "staffjoy-faraday";

    public static final String CURRENT_USER_HEADER = "faraday-current-user-id";

    public static final String AUTHORIZATION_HEADER = "Authorization";

    /**
     * 对没有进行身份验证的用户打上标记
     */
    public static final String AUTHORIZATION_ANONYMOUS_WEB = "faraday-anonymous";

    /**
     * 公司服务部门打上标记
     */
    public static final String AUTHORIZATION_COMPANY_SERVICE = "company-service";

    /**
     * 公司机器人打上标记
     */
    public static final String AUTHORIZATION_BOT_SERVICE = "bot-service";


    /**
     * 财务用户打上标记
     */
    public static final String AUTHORIZATION_ACCOUNT_SERVICE = "account-service";

    /**
     * 开发团队打上标记
     */
    public static final String AUTHORIZATION_SUPPORT_USER = "faraday-support";

    /**
     * 仅用于开发时，最大权限
     */
    public static final String AUTHORIZATION_SUPERPOWERS_SERVICE = "supperpowers_service";

    /**
     * 表示正在登陆或者时注册的用户打上标记
     */
    public static final String AUTHORIZATION_WWW_SERVICE = "www-service";

    public static final String AUTHORIZATION_WHOAMI_SERVICE = "whoami-service";

    public static final String AUTHORIZATION_AUTHENTICATED_USER = "faraday-authenticated";

    public static final String AUTHORIZATION_ICAL_SERVICE = "ical-service";

    /**
     * 错误信息
     */
    public static final String ERROR_MSG_DO_NOT_HAVE_ACCESS = "You do not have access to this service";
    public static final String ERROR_MSG_MISSING_AUTH_HEADER = "Missing Authorization http header";

}
