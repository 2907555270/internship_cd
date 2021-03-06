package com.txy.graduate.controller;

import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.txy.graduate.config.Result;
import com.txy.graduate.domain.po.SysMenu;
import com.txy.graduate.domain.po.SysUser;
import com.txy.graduate.config.ConstConfig;
import com.txy.graduate.domain.dto.MenuDTO;
import com.txy.graduate.service.ISysMenuService;
import com.txy.graduate.service.ISysUserService;
import com.txy.graduate.util.QueryUtil;
import com.txy.graduate.util.RedisUtil;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/sys/menu")
public class MenuController {

    @Resource
    private ISysUserService userService;
    @Resource
    private ISysMenuService menuService;
    @Resource
    private RedisUtil redisUtil;


    /**
     * menu表
     */
    @PreAuthorize("hasAuthority('sys:menu:list')")
    @GetMapping("/list/{currentPage}/{pageSize}")
    public Result findAll(@PathVariable int currentPage, @PathVariable int pageSize){
        Map<String, Object> map = QueryUtil.getMapFromPage(currentPage, pageSize);

        IPage<SysMenu> page = menuService.querySysMenu(map);
        boolean flag = page.getSize() > 0;
        return Result.result(flag?200:404,flag,flag?"查询成功 ^_^":"未获取到任何数据 -_-",page);
    }

    //多条件+分页查询
    @PreAuthorize("hasAuthority('sys:menu:list')")
    @PostMapping("/list")
    public Result findSysMenu(@RequestBody Map<String,Object> map){
        IPage<SysMenu> page = menuService.querySysMenu(map);
        boolean flag = page.getSize() > 0;
        return Result.result(flag?200:404,flag,flag?"查询成功 ^_^":"查询失败 -_-",page);
    }

    //添加新的菜单信息
    @PreAuthorize("hasAuthority('sys:menu:save')")
    @PutMapping("/save")
    public Result saveSysMenu(@RequestBody @Validated SysMenu sysMenu, BindingResult result){
        //效验数据
        List<FieldError> fieldErrors = result.getFieldErrors();
        //存在错误
        if(!fieldErrors.isEmpty())
            return Result.result(false,null,fieldErrors.get(0).getDefaultMessage());

        boolean flag = menuService.save(sysMenu);
        return Result.result(flag?200:500,flag,flag?"添加成功 ^_^":"添加失败 -_-",null);
    }

    //按menu的主键id更新菜单信息
    @PreAuthorize("hasAuthority('sys:menu:update')")
    @PostMapping("/update")
    public Result updateSysMenu(@RequestBody SysMenu sysMenu){
        boolean flag = menuService.updateById(sysMenu);
        return Result.result(flag?200:500,flag,flag?"修改成功 ^_^":"修改失败 -_-",null);
    }

    //按menu_id删除菜单信息，同时解绑该菜单上的角色信息
    @PreAuthorize("hasAuthority('sys:menu:delete')")
    @DeleteMapping("/delete/{menu_id}")
    public Result deleteSysMenuByMid(@PathVariable Long menu_id){
        boolean flag = menuService.deleteSysMenuById(menu_id);
        return Result.result(flag?200:500,flag,flag?"删除成功 ^_^":"删除失败 -_-",null);
    }



    /**
     * user user_role role_menu menu表
     */
    //获取导航栏：任何用户都可以访问
    @GetMapping("/getNav")
    public Result getNav(){
        //从上下文中获取用户的姓名
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        //从redis中获取用户信息
        SysUser sysUser = (SysUser) redisUtil.get(ConstConfig.USER_KEY+":"+username);

        // 获取权限信息
        String authorityInfo = userService.queryUserAuthorityInfo(sysUser.getId());
        String[] authorityArr = authorityInfo.split(",");

        // 获取导航栏
        List<MenuDTO> navs = menuService.queryCurrentUserNav(username);
        boolean flag = !navs.isEmpty();

        return Result.result(flag?200:400,flag,
                flag?"菜单信息获取成功 ^_^":"未获取到任何菜单信息 -_-",
                 MapUtil.builder()
                .put("authorities",authorityArr)
                .put("nav",navs)
                .map());
    }



}
