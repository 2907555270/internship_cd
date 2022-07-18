package com.txy.graduate.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.txy.graduate.config.Result;
import com.txy.graduate.domain.po.SysUser;
import com.txy.graduate.service.ISysUserService;
import com.txy.graduate.util.QueryUtil;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;


@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private ISysUserService userService;


    /**
     * user表
     */
    //分页查询所有数据
    @PreAuthorize("hasAuthority('sys:user:list')")
    @GetMapping("/list/{currentPage}/{pageSize}")
    public Result findAll(@PathVariable int currentPage,@PathVariable int pageSize){
        //数据封装到map中
        Map<String, Object> map = QueryUtil.getMapFromPage(currentPage, pageSize);

        //执行查询操作，获取数据
        IPage<SysUser> page = userService.querySysUser(map);
        boolean flag = page.getSize()>0;

        //返回操作结果
        return Result.result(flag?200:400,flag,flag?"查询成功 ^_^":"未获取到任何数据 -_-",page);
    }

    //分页+多条件模糊查询
    @PreAuthorize("hasAuthority('sys:user:list')")
    @PostMapping("/select")
    public Result findSysUsers(@RequestBody Map<String,Object> map){
        IPage<SysUser> page = userService.querySysUser(map);
        boolean flag = page.getSize()>0;
        return Result.result(flag?200:400,flag,flag?"查询成功 ^_^":"未获取到任何数据 -_-",page);
    }

    //更新用户信息：无权修改权限信息，由RoleController统一控制
    @PreAuthorize("hasAuthority('sys:user:update')")
    @PostMapping("/update")
    public Result updateSysUser(@RequestBody SysUser sysUser){
        boolean flag = userService.updateById(sysUser);
        return Result.result(flag?200:500,flag,flag?"更新成功 ^_^":"更新失败 -_-",null);
    }

    /**
     *  user user_role表
     */
    //删除用户，同时解绑用户的角色信息
    @PreAuthorize("hasAuthority('sys:user:delete')")
    @DeleteMapping("/delete/{user_id}")
    public Result deleteSysUser(@PathVariable Long user_id){
        boolean flag = userService.deleteUserAndUserRoleByUid(user_id);
        return Result.result(flag?200:500,flag,flag?"删除成功 ^_^":"删除失败 -_-",null);
    }

    //添加新用户，同时绑定用户的角色信息
    @PreAuthorize("hasAuthority('sys:user:save')")
    @PutMapping("/save")
    public Result saveSysUser(@RequestBody Map<String,Object> map){
        boolean flag = userService.saveUserAndUserRole(map);
        return Result.result(flag?200:500,flag,flag?"添加成功 ^_^":"添加失败 -_-",null);
    }
}
