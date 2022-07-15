package com.txy.graduate.util;

import com.txy.graduate.config.Result;
import com.txy.graduate.service.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class FileUtilTest {

    @Autowired
    private ProcessService processService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private ISysMenuService sysMenuService;

    @Autowired
    private ISysUserService sysUserService;

    @Autowired
    private ISysRoleService sysRoleService;

    @Autowired
    private FileUtil fileUtil;

    @Test
    public void testStudentDataExport(){
        Result result = fileUtil.exportDataToExcel(studentService.queryAll(),"StudentData","student");
        System.out.println(result);
    }

    @Test
    public void testProcessDataExport(){
        Result result = fileUtil.exportDataToExcel(processService.queryAll(), "ProcessData", "process");
        System.out.println(result);
    }

    @Test
    public void testMenuDataExport(){
        Result result = fileUtil.exportDataToExcel(sysMenuService.queryAll(), "MenuData", "menu");
        System.out.println(result);
    }

    @Test
    public void testUserDataExport(){
        Result result = fileUtil.exportDataToExcel(sysUserService.queryAll(), "UserData", "user");
        System.out.println(result);
    }

    @Test
    public void testRoleDataExport(){
        Result result = fileUtil.exportDataToExcel(sysRoleService.queryAll(),null,null);
        System.out.println(result);
    }
}
