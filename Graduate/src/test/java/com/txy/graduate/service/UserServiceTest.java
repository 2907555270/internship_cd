package com.txy.graduate.service;

import com.txy.graduate.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class UserServiceTest {
    @Autowired
    private UserService userService;

    @Test
    public void test_findAllByPage(){
        System.out.println(userService.findAllByPage(1,2).getRecords());
    }

    @Test
    public void test_login(){
        User user = new User();
        user.setUserId("abcd");
        user.setUserPwd("abcd");
        User login = userService.login(user);
        System.out.println(login);
    }
}
