package com.txy.graduate.security.exception;

/**
 * 此异常并非异常，而是为了Service层的事务而抛出的人为异常
 */
public class TrapException extends Exception{
    public TrapException(String message, Throwable cause) {

    }
}
