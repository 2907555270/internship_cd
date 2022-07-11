package com.txy.graduate.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.txy.graduate.domain.SysUser;
import com.txy.graduate.domain.SysUserRole;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysUserMapper extends BaseMapper<SysUser>{
    //通过userId查询 Menu
    List<String> getMenuByUserId(Long userId);

    //通过userId查询 MenuId
    List<Long> getMenuIdByUserId(Long userId);

    //通过menuId查询
    List<SysUserRole> ListByMenuId(Long menuId);
}
