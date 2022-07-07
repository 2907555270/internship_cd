package com.example.demo.service;

import com.example.demo.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class UserServiceTest {
    @Resource
    private UserService userService;

    @Test
    public void test(){
        for (int i = 0; i < 10; i++) {
            User user = userService.selectByPrimaryKey(2);
        }
        //System.out.println(user);
    }
}
