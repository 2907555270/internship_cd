package com.txy.graduate.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.txy.graduate.config.Result;
import com.txy.graduate.domain.Role;
import com.txy.graduate.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    //分页查询所有的数据
    @GetMapping("{currentPage}/{pageSize}")
    public Result findAll(@PathVariable int currentPage,@PathVariable int pageSize){
        IPage<Role> page = roleService.findAllByPage(currentPage, pageSize);
        Boolean flag = page.getSize()>0;
        return  new Result(flag,page,flag?null:"未获取任何数据 -_-");
    }

    //根据用户的user_id查询用户的权限
    @GetMapping("{user_id}")
    public Result getRole(@PathVariable String user_id){
        Role role = roleService.getRole(user_id);
        return new Result(true,role);
    }
}
