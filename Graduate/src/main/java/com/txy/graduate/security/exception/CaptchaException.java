package com.txy.graduate.security.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * 验证码错误异常
 */
public class CaptchaException extends AuthenticationException {
    public CaptchaException(String msg){
        super(msg);
    }
}
