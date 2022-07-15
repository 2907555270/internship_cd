package com.txy.graduate.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Result implements Serializable {
    private int code;
    private boolean flag;
    private String msg;
    private Object data;


    //测试时的响应内容
    public static Result resp(int code,String msg, Object data){
        Result result = new Result();
        result.setCode(code);
        result.setData(data);
        result.setMsg(msg);
        return result;
    }

    //用户使用的响应内容
    public static Result result(int code,boolean flag,String msg,Object data){
        Result result = new Result();
        result.setCode(code);
        result.setFlag(flag);
        result.setMsg(msg);
        result.setData(data);
        return result;
    }

    //用户使用的响应内容
    public static Result result(boolean flag,Object data,String msg){
        Result result = new Result();
        result.setFlag(flag);
        result.setMsg(msg);
        result.setData(data);
        return result;
    }
}
