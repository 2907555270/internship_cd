package com.txy.graduate.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.txy.graduate.config.Result;
import com.txy.graduate.domain.po.StudentInfo;


import com.txy.graduate.service.StudentService;
import com.txy.graduate.util.FileUtil;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/student")
public class StudentController {

    @Resource(name = "studentService")
    private StudentService studentService;

    @Resource(name = "fileUtil")
    private FileUtil fileUtil;


    //获取 分页查询所有数据
    @PreAuthorize("hasRole('admin')")
    @GetMapping("/list/{schoolCode}/{currentPage}/{pageSize}")
    public Result findByPage(@PathVariable String schoolCode, @PathVariable int currentPage, @PathVariable int pageSize) {
        if (currentPage <= 0)
            currentPage = 1;
        if (pageSize <= 0)
            pageSize = 4;
        IPage<StudentInfo> studentInfoIPage = studentService.queryByPage(schoolCode, currentPage, pageSize);

        boolean flag = studentInfoIPage.getTotal() > 0;

        return Result.result(flag, studentInfoIPage, flag ? "查询成功 ^_^" : "未获取到任何数据 -_-");
    }

    //获取 按多种条件组合查询所有数据
    @PreAuthorize("hasRole('admin')")
    @PostMapping("/select/{schoolCode}/{currentPage}/{pageSize}")
    public Result findByConditions(@RequestBody Map<String, Object> map, @PathVariable String schoolCode,
                                   @PathVariable int currentPage, @PathVariable int pageSize) {
        if (currentPage <= 0)
            currentPage = 1;
        if (pageSize <= 0)
            pageSize = 4;

        IPage<List<Map<String, Object>>> listIPage = studentService.queryByConditionsAndPage(map, schoolCode, currentPage, pageSize);

        boolean flag = listIPage.getTotal() > 0;

        return Result.result(flag, listIPage, flag ? "数据查询成功 ^_^" : "未查询到任何信息 -_-");
    }

    //获取 根据输入框实时提示：单个输入，可能是学号，也可能是姓名查询学生信息
    @PreAuthorize("hasRole('admin')")
    @GetMapping("/search/{schoolCode}/{content}")
    public Result searchTip(@PathVariable String schoolCode, @PathVariable String content) {
        IPage<StudentInfo> studentInfoIPage = studentService.queryByIdOrName(content, schoolCode);

        boolean flag = studentInfoIPage.getTotal() > 0;

        return Result.result(flag, studentInfoIPage, flag ? "查询成功 ^_^" : "查询失败 -_-");
    }

    //获取 学生的毕业手续 所有状态的 完成和未完成人数
    @PostMapping("/statistic")
    public Result statistic(@RequestBody StudentInfo studentInfo) {
        Map<String, Map<String, Object>> statuses = studentService.queryStatus(studentInfo);
        boolean flag = !statuses.isEmpty();
        return Result.result(flag, statuses, flag ? null : "数据获取失败 -_-");
    }

    //获取 根据id获取单个学生的数据
    @GetMapping("/one/{id}")
    public Result findById(@PathVariable Long id) {
        Map<String, Object> map = studentService.queryById(id);
        boolean flag = !map.isEmpty();
        return Result.result(flag, map, flag ? null : "未找到该学生的信息 -_-");
    }

    //获取系部、专业、班级的导航信息
    @GetMapping("/list/nav/{schoolCode}")
    public Result findNav(@PathVariable String schoolCode) {
        Map<String, Object> map = studentService.queryNav(schoolCode);
        boolean flag = !map.isEmpty();
        return Result.result(flag, map, flag ? null : "未获取数据");
    }


    //获取 根据id获取指定的学生数据
    @PostMapping("/one/part")
    public Result findPartById(@RequestBody Map<String, Object> map) {
        Map<String, Object> result = studentService.queryPartInfoById(map);
        boolean flag = result != null;
        return Result.result(flag, result, flag ? null : "未找到该学生的信息 -_-");
    }

    //修改学生的状态信息
    @PreAuthorize("hasRole('admin')")
    @PostMapping("/update/status")
    public Result updateStatus(@RequestBody Map<String, Object> map) {
        boolean flag = studentService.updateStatus(map);
        return Result.result(flag, null, flag ? "修改成功 ^_^" : "修改失败 -_-");
    }


    //导出学生信息
    @GetMapping("/export/{schoolCode}")
    public Result StudentDataExport(@PathVariable String schoolCode, String fileName, String sheetName) {
        return fileUtil.exportDataToExcel(studentService.queryAll(schoolCode), fileName, sheetName);
    }

    /**
     * *********************************** 项目无关的方法 ***************************************
     */
    //修改 学生信息(仅此)
    //无权修改学生的权限信息，由RoleController统一控制
    //无权修改学生的用户信息，由UserController统一控制


    //添加 学生信息：同时为学生开通user账户 设置相应的权限
    @PreAuthorize("hasRole('admin')")
    @PutMapping("/save")
    public Result save(@RequestBody @Validated StudentInfo student, BindingResult result) {
        //student数据验证
        List<FieldError> fieldErrors = result.getFieldErrors();
        if (!fieldErrors.isEmpty()) {
            return Result.result(500, false, fieldErrors.get(0).getDefaultMessage(), null);
        }

        //学号重复验证
        if (studentService.queryByStudentId(student.getStudentId()) != null)
            return Result.result(500, false, "添加失败,当前学号已存在 -_-", null);

        //添加学生student信息
        boolean flag = studentService.saveStudent(student);

        return Result.result(flag ? 200 : 500, flag, flag ? "添加成功 ^_^" : "添加失败 -_-", null);
    }

    //删除 学生信息：同时删除学生user账户以及学生的权限信息
    @PreAuthorize("hasRole('admin')")
    @DeleteMapping("/delete/{id}")
    public Result deleteById(@PathVariable Long id) {
        return studentService.removeStudent(id);
    }
}

