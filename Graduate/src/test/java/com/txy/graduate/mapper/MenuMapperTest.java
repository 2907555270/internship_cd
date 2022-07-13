package com.txy.graduate.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.txy.graduate.domain.po.SysMenu;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MenuMapperTest {
    @Autowired
    private SysMenuMapper menuMapper;

    @Test
    public void Test(){
        Long[] menuIds = {3L,6L,7L,8L,9L};
        menuMapper.selectList(new QueryWrapper<SysMenu>().in("id", menuIds));
    }
}
