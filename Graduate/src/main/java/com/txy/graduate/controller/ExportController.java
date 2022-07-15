package com.txy.graduate.controller;

import com.txy.graduate.config.Result;
import com.txy.graduate.service.*;
import com.txy.graduate.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 导出数据控制层
 */
@RestController
@RequestMapping("/sys/export")
public class ExportController {

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

    @GetMapping("/student")
    public Result StudentDataExport(String fileName,String sheetName) {
        return fileUtil.exportDataToExcel(studentService.queryAll(), fileName,sheetName);
    }

    @GetMapping("/process")
    public Result ProcessDataExport(String fileName,String sheetName) {
        return fileUtil.exportDataToExcel(processService.queryAll(), fileName,sheetName);
    }

    @GetMapping("/menu")
    public Result MenuDataExport(String fileName,String sheetName) {
        return fileUtil.exportDataToExcel(sysMenuService.queryAll(), fileName,sheetName);
    }

    @GetMapping("/user")
    public Result UserDataExport(String fileName,String sheetName) {
        return fileUtil.exportDataToExcel(sysUserService.queryAll(), fileName,sheetName);
    }

    @GetMapping("/role")
    public Result RoleDataExport(String fileName,String sheetName) {
        return fileUtil.exportDataToExcel(sysRoleService.queryAll(), fileName,sheetName);
    }
}
