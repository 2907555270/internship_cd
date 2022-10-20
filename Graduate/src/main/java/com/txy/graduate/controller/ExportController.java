package com.txy.graduate.controller;

import com.txy.graduate.config.Result;
import com.txy.graduate.service.*;
import com.txy.graduate.util.FileUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


/**
 * 导出数据控制层
 */
@RestController
@RequestMapping("/sys/export")
public class ExportController {

    @Resource
    private ProcessService processService;

    @Resource
    private StudentService studentService;

    @Resource
    private ISysMenuService sysMenuService;

    @Resource
    private ISysUserService sysUserService;

    @Resource
    private ISysRoleService sysRoleService;

    @Resource
    private FileUtil fileUtil;

    @GetMapping("/student")
    public Result StudentDataExport(String fileName,String sheetName) {
        return fileUtil.exportDataToExcel(studentService.queryAll("sc1010"), fileName,sheetName);
    }

    @GetMapping("/process")
    public Result ProcessDataExport(String fileName,String sheetName) {
        return fileUtil.exportDataToExcel(processService.queryAll("sc1010"), fileName,sheetName);
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
