package com.txy.graduate.security.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * 验证码错误异常
 */
public class CaptcheException extends AuthenticationException {
    public CaptcheException(String msg){
        super(msg);
    }
}
