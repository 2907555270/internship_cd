package com.txy.graduate.config;


/**
 * 固定的默认配置类
 */
public class ConstConfig {
    //redis中的验证码Key
    public final static String CAPTCHA_KEY = "captcha";

    //redis中的用户信息Key
    public final static String USER_KEY = "sys_user";

    //redis中的授权信息Key
    public final static String GRANTED_KEY = "granted_authority";
}
