package com.txy.graduate.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.txy.graduate.config.Result;
import com.txy.graduate.domain.po.SysRole;
import com.txy.graduate.domain.po.SysRoleMenu;
import com.txy.graduate.domain.po.SysUserRole;
import com.txy.graduate.service.ISysRoleService;
import com.txy.graduate.util.QueryUtil;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
//TODO:访问权限没有设置完整

/**
 * Role 角色类，仅限管理员访问
 */
@RestController
@RequestMapping("/role")
public class RoleController {

    @Resource
    private ISysRoleService roleService;

    /**
     * Role表
     */
    @PreAuthorize("hasAuthority('sys:role:list')")
    //分页查询所有的数据
    @GetMapping("/list/{currentPage}/{pageSize}")
    public Result findAll(@PathVariable int currentPage,@PathVariable int pageSize){
        //封装分页信息
        Map<String, Object> map = QueryUtil.getMapFromPage(currentPage, pageSize);
        //执行查询
        IPage<SysRole> page = roleService.querySysRole(map);
        boolean flag = page.getTotal()>0;
        return Result.result(flag?200:404,flag, flag ? null : "未获取任何数据 -_-", page);
    }

    @PreAuthorize("hasAuthority('sys:role:list')")
    //按条件+分页查询数据
    @PostMapping("/select")
    public Result findRoles(@RequestBody Map<String,Object> map){
        IPage<SysRole> page = roleService.querySysRole(map);
        boolean flag = page.getTotal()>0;
        return Result.result(flag?200:404,flag,flag?"查询成功 ^_^":"查询失败 -_-",page);
    }

    @PreAuthorize("hasAuthority('sys:role:save')")
    //添加新的角色
    @PutMapping("/save")
    public Result save(@RequestBody SysRole role){
        boolean flag = roleService.save(role);
        return Result.result(flag?200:500,flag,flag?"添加成功 ^_^":"添加失败 -_-",null);
    }

    @PreAuthorize("hasAuthority('sys:role:update')")
    //根据id修改角色信息: 需求中不一定用得到
    @PostMapping("/update")
    public Result updateById(@RequestBody SysRole role){
        boolean flag = roleService.updateById(role);
        return Result.result(flag?200:500,flag,flag?"修改成功 ^_^":"修改失败 -_-",null);
    }


    /**
     * role user_role menu_role表
     */
    @PreAuthorize("hasAnyAuthority('sys:role:delete')")
    //根据id删除角色：同时需要解绑用户的角色信息,以及解绑菜单的角色信息
    @DeleteMapping("/delete/{role_id}")
    public Result deleteById(@PathVariable Long role_id){
        boolean flag = roleService.removeRoleByRid(role_id);
        return Result.result(flag?200:500,flag,flag?"删除成功 ^_^":"删除失败 -_-",null);
    }

    /**
     * user_role表
     */
    @PreAuthorize("hasAuthority('sys:role:list')")
    //根据用户的user_id查询用户的角色
    @GetMapping("/user/select/{user_id}")
    public Result getRoleByUid(@PathVariable Long user_id){
        List<SysRole> roles = roleService.queryRoleByUid(user_id);
        boolean flag = roles.size()>0;
        return Result.result(flag?200:404,flag,flag?"查询成功":"暂未获取到任何数据 -_-",roles);
    }

    @PreAuthorize("hasAnyAuthority('sys:role:save','sys:role:update')")
    //为用户绑定新的角色信息
    @PutMapping("/user/bind")
    public Result bindUserRole(@RequestBody SysUserRole sysUserRole){
        boolean flag = roleService.saveUserAndRole(sysUserRole);
        return Result.result(flag?200:500,flag,flag?"绑定成功 ^_^":"绑定失败 -_-",null);
    }

    @PreAuthorize("hasAnyAuthority('sys:role:update','sys:role:delete')")
    //为用户解绑角色:按user_id
    @DeleteMapping("/user/unbind/{user_id}")
    public Result unBindUserRole(@PathVariable Long user_id){
        boolean flag = roleService.removeUserAndRoleByUId(user_id);
        return Result.result(flag?200:500,flag,flag?"解绑成功 ^_^":"解绑失败 -_-",null);
    }

    @PreAuthorize("hasAuthority('sys:role:update')")
    //修改用户绑定的角色信息
    @PostMapping("/user/update")
    public Result UpdateBindUserRoleById(@RequestBody SysUserRole sysUserRole){
        boolean flag = roleService.updateUByUserRoleId(sysUserRole);
        return Result.result(flag?200:500,flag,flag?"改绑成功 ^_^":"改绑失败 -_-",null);
    }


    /**
     * menu_role表
     */
    @PreAuthorize("hasAuthority('sys:role:list')")
    //根据menu_id查询角色信息
    @GetMapping("/menu/{menu_id}")
    public Result getRoleByMId(@PathVariable Long menu_id){
        List<SysRole> roles = roleService.queryRoleByMid(menu_id);
        boolean flag = roles.size()>0;
        return Result.result(flag?200:404,flag,flag?"查询成功 ^_^":"查询失败 -_-",roles);
    }

    @PreAuthorize("hasAnyAuthority('sys:role:save','sys:role:update')")
    //为menu绑定role
    @PutMapping("/menu/bind")
    public Result BindRoleMenu(@RequestBody SysRoleMenu sysRoleMenu){
        boolean flag = roleService.saveRoleAndMenu(sysRoleMenu);
        return Result.result(flag?200:500,flag,flag?"绑定成功 ^_^":"绑定失败 -_-",null);
    }

    @PreAuthorize("hasAnyAuthority('sys:role:delete','sys:role:update')")
    //为menu解绑role : 按menu_id
    @DeleteMapping("/menu/unbind/{menu_id}")
    public Result UnbindRoleMenuByMid(@PathVariable Long menu_id){
        boolean flag = roleService.removeRoleAndMenuByMid(menu_id);
        return Result.result(flag?200:500,flag,flag?"解绑成功 ^_^":"解绑失败 -_-",null);
    }

    //改绑menu和role
    @PreAuthorize("hasAuthority('sys:role:update')")
    @PostMapping("/menu/update")
    public Result UpdateRoleMenuById(@RequestBody SysRoleMenu sysRoleMenu){
        boolean flag = roleService.updateMByUserRoleId(sysRoleMenu);
        return Result.result(flag?200:500,flag,flag?"改绑成功 ^_^":"改绑失败 -_-",null);
    }
}
