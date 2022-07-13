package com.txy.graduate.util;

import java.util.UUID;

public class ToolUtil {

    public static String getUUID(){
        return UUID.randomUUID().toString().replace("-","").toLowerCase();
    }

    public static void main(String[] args) {
        System.out.println(getUUID());
    }
}
