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
        boolean flag = page.getSize()>0;
        return  new Result(flag,page,flag?null:"未获取任何数据 -_-");
    }

    //根据用户的user_id查询用户的权限
    @GetMapping("{user_id}")
    public Result getRole(@PathVariable String user_id){
        Role role = roleService.getRole(user_id);
        return new Result(true,role);
    }

/**
 ************************************ 带逻辑增删改相关接口 ***************************************
 */
    //根据id删除角色
    @DeleteMapping("{role_id}")
    public Result deleteById(@PathVariable Integer role_id){
        //删除user_role中role_id对应的数据
        boolean flag1 = roleService.removeUserAndRoleByRId(role_id);

        //删除role中role_id对应的项
        boolean flag2 = roleService.removeById(role_id);

        return new Result(flag1&&flag2,null,flag1&&flag2?"删除成功 ^_^":"删除失败 -_-");
    }

/**
 ************************************ 简单增删改相关接口 ***************************************
 */

    //添加新的角色
    @PutMapping()
    public Result save(@RequestBody Role role){
        boolean flag = roleService.save(role);
        return new Result(flag,null,flag?"添加成功 ^_^":"添加失败 -_-");
    }

    //根据id修改角色: 需求中不一定用得到
    @PostMapping()
    public Result updateById(@RequestBody Role role){
        boolean flag = roleService.updateById(role);
        return new Result(flag,null,flag?"修改成功 ^_^":"修改失败 -_-");
    }
}
