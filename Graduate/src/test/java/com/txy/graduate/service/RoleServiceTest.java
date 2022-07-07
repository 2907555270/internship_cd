package com.txy.graduate.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RoleServiceTest {
    @Autowired
    private RoleService roleService;

    @Test
    public void test_findAllByPage(){
        System.out.println(roleService.findAllByPage(1,2).getRecords());
    }
}
