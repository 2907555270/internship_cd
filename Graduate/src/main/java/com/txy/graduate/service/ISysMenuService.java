package com.txy.graduate.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.txy.graduate.domain.sys.SysMenu;
import com.txy.graduate.domain.dto.MenuDTO;

import java.util.List;
import java.util.Map;

public interface ISysMenuService extends IService<SysMenu> {

    /**
     * menu表
     */
    //分页+多条件模糊查询
    IPage<SysMenu> findSysMenu(Map<String,Object> map);

    /**
     * menu role_menu表
     */
    //按menu_id删除菜单信息
    boolean deleteSysMenuById(Long menu_id);

    /**
     * user_role + role_menu表
     */
    //从redis或上下文中获取当前用户的信息 ---> 当前用户可使用的Menu
    List<MenuDTO> getCurrentUserNav(String username);

}
