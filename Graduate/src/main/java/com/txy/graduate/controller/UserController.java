package com.txy.graduate.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.txy.graduate.config.Result;
import com.txy.graduate.domain.sys.SysUser;
import com.txy.graduate.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    private ISysUserService userService;


    /**
     * user表
     */
    //分页查询所有数据
    @GetMapping("{currentPage}/{pageSize}")
    public Result findAll(@PathVariable int currentPage,@PathVariable int pageSize){
        //数据封装到map中
        HashMap<String, Object> map = new HashMap<>();
        map.put("currentPage",currentPage);
        map.put("pageSize",pageSize);

        //执行查询操作，获取数据
        IPage<SysUser> page = userService.findSysUser(map);
        boolean flag = page.getSize()>0;

        //返回操作结果
        return Result.result(flag,page,flag?null:"未获取到任何数据 -_-");
    }

    //分页+多条件模糊查询
    @PostMapping("select")
    public Result findSysUsers(@RequestBody Map<String,Object> map){
        IPage<SysUser> page = userService.findSysUser(map);
        boolean flag = page.getSize()>0;
        return Result.result(flag,page,flag?null:"未获取到任何数据 -_-");
    }

    //更新用户信息
    @PostMapping()
    public Result updateSysUser(@RequestBody SysUser sysUser){
        boolean flag = userService.updateById(sysUser);
        return Result.result(flag,null,flag?"更新成功 ^_^":"更新失败 -_-");
    }

    /**
     *  user user_role表
     */
    //删除用户，同时解绑用户的角色信息
    @DeleteMapping("{user_id}")
    public Result deleteSysUser(@PathVariable Integer user_id){
        boolean flag = userService.deleteUserAndUserRoleByUid(user_id);
        return Result.result(flag,null,flag?"删除成功 ^_^":"删除失败 -_-");
    }

    //添加新用户，同时绑定用户的角色信息
    @PutMapping("save")
    public Result saveSysUser(@RequestBody Map<String,Object> map){
        boolean flag = userService.saveUserAndUserRole(map);
        return Result.result(flag,null,flag?"添加成功 ^_^":"添加失败 -_-");
    }
}
