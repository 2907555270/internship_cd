package com.txy.graduate.config;

import lombok.Data;

@Data
public class Result {
    private Boolean flag;
    private Object data;
    private String msg;

    public Result(Boolean flag,Object data){
        this.flag = flag;
        this.data = data;
    }

    public Result(Boolean flag,Object data,String msg){
        this.flag = flag;
        this.data = data;
        this.msg = msg;
    }
}
