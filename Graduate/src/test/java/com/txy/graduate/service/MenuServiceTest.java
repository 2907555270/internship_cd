package com.txy.graduate.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MenuServiceTest {

    @Autowired
    private ISysMenuService sysMenuService;

    @Test
    public void test(){
        //List<MenuDTO> userNav = sysMenuService.queryCurrentUserNav("");
        //System.out.println(userNav.toString());
    }
}
