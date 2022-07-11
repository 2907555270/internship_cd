package com.txy.graduate.service;

import com.txy.graduate.security.entity.MenuDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class MenuServiceTest {

    @Autowired
    private ISysMenuService sysMenuService;

    @Test
    public void test(){
        List<MenuDTO> userNav = sysMenuService.getCurrentUserNav();
        System.out.println(userNav.toString());
    }
}
