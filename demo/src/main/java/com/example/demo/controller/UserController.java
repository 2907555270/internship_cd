package com.example.demo.controller;

import com.example.demo.domain.User;
import com.example.demo.service.Impl.UserServiceImpl;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserServiceImpl userServiceImpl;

    @GetMapping("selectOne")
    public User selectOne(Integer id) {
        return userServiceImpl.selectByPrimaryKey(id);
    }

}
