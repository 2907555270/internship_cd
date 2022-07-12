package com.txy.graduate.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.txy.graduate.domain.sys.SysRole;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
public class RoleServiceTest {
    @Autowired
    private ISysRoleService roleService;

    private final Map<String,Object> map = new HashMap<>();
    {
        map.put("currentPage",1);
        map.put("pageSize",2);
        //map.put("code","p1");
        map.put("status",1);
    }

    @Test
    public void test_findSysRole(){
        IPage<SysRole> sysRole = roleService.querySysRole(map);
        System.out.println(sysRole.getRecords());
    }
}
