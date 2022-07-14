package com.txy.graduate.util;

import java.util.UUID;

public class ToolUtil {

    //生成无符号的UUID
    public static String getUUID(){
        return UUID.randomUUID().toString().replace("-","").toLowerCase();
    }

    //导出excel

    public static void main(String[] args) {
        System.out.println(getUUID());
    }
}
