package com.txy.graduate.controller;

import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.txy.graduate.config.Result;
import com.txy.graduate.domain.SysMenu;
import com.txy.graduate.domain.SysUser;
import com.txy.graduate.security.config.ConstConfig;
import com.txy.graduate.security.entity.MenuDTO;
import com.txy.graduate.service.ISysMenuService;
import com.txy.graduate.service.ISysUserService;
import com.txy.graduate.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/sys/menu")
public class MenuController {

    @Autowired
    private ISysUserService userService;
    @Autowired
    private ISysMenuService menuService;
    @Autowired
    private RedisUtil redisUtil;


    /**
     * menu表
     */
    @PostMapping("/select")
    public Result findSysMenu(@RequestBody Map<String,Object> map){
        IPage<SysMenu> page = menuService.findSysMenu(map);
        boolean flag = page.getSize() > 0;
        return Result.result(flag,page,flag?"查询成功 ^_^":"查询失败 -_-");
    }

    @PutMapping()
    public Result saveSysMenu(@RequestBody SysMenu sysMenu){
        boolean flag = menuService.save(sysMenu);
        return Result.result(flag,null,flag?"添加成功 ^_^":"添加失败 -_-");
    }

    @PostMapping()
    public Result updateSysMenu(@RequestBody SysMenu sysMenu){
        boolean flag = menuService.updateById(sysMenu);
        return Result.result(flag,null,flag?"修改成功 ^_^":"修改失败 -_-");
    }

    @DeleteMapping("{menu_id}")
    public Result deleteSysMenuByMid(@PathVariable Integer menu_id){
        boolean flag = menuService.deleteSysMenuById(menu_id);
        return Result.result(flag,null,flag?"删除成功 ^_^":"删除失败 -_-");
    }



    /**
     * user user_role role_menu menu表
     */
    //获取导航栏
    @PreAuthorize("hasRole('admin')")
    @GetMapping("/getNav")
    public Result getNav(Principal principal){
        //从redis获取用户信息
        SysUser sysUser = (SysUser) redisUtil.get(ConstConfig.USER_KEY);

        // 获取权限信息
        String authorityInfo = userService.getUserAuthorityInfo(sysUser.getId());
        //if (authorityInfo.endsWith(",")){
        //    authorityInfo = authorityInfo.substring(0,authorityInfo.length());
        //}
        String[] authorityArr = authorityInfo.split(",");

        // 获取导航栏
        List<MenuDTO> navs = menuService.getCurrentUserNav();

        return Result.resp(200, "ok",
                 MapUtil.builder()
                .put("authorities",authorityArr)
                .put("nav",navs)
                .map());
    }



}
