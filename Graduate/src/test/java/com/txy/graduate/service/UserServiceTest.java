package com.txy.graduate.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.txy.graduate.domain.sys.SysUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;


@SpringBootTest
public class UserServiceTest {
    @Autowired
    private ISysUserService userService;

    public HashMap<String,Object> map = new HashMap<>();
    {
        map.put("currentPage",1);
        map.put("pageSize",2);
        map.put("status",1);
        //map.put("username","zhangsan");
    }

    @Test
    public void test_findAllByPage(){
        IPage<SysUser> sysUser = userService.findSysUser(map);
        System.out.println(sysUser.getRecords());
    }
}
