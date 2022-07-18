package com.txy.graduate.controller;

import com.txy.graduate.config.Page;
import com.txy.graduate.config.Result;
import com.txy.graduate.domain.dto.StudentDto;
import com.txy.graduate.domain.po.Student;


import com.txy.graduate.domain.vo.Status;
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
    @GetMapping("/list/{currentPage}/{pageSize}")
    public Result findAll(@PathVariable int currentPage, @PathVariable int pageSize) {
        Page<StudentDto> dtoPage = studentService.queryByPage(new Page<>(currentPage, pageSize));
        boolean flag = dtoPage.getTotal() > 0;
        return Result.result(flag ? 200 : 404, flag, flag ? "查询成功 ^_^" : "未获取到任何数据 -_-", dtoPage);
    }

    //获取 按多种条件组合查询所有数据
    @PreAuthorize("hasRole('admin')")
    @PostMapping("/select")
    public Result findByConditions(@RequestBody Map<String, Object> map) {
        Page<StudentDto> dotPage = studentService.queryByConditionsAndPage(map);
        boolean flag = dotPage.getTotal() > 0;
        return Result.result(flag ? 200 : 404, flag, flag ? "数据查询成功 ^_^" : "未查询到任何信息 -_-", dotPage);
    }


    //获取 根据输入框实时提示：单个输入，可能是学号，也可能是姓名查询学生信息
    @GetMapping("/search/{content}")
    public Result searchTip(@PathVariable String content) {
        List<Student> students = studentService.queryByIdOrName(content);
        boolean flag = students.size() > 0;
        return Result.result(flag ? 200 : 404, flag, null, students);
    }

    //获取 根据id获取单个学生的数据
    @GetMapping("/one/{id}")
    public Result findById(@PathVariable Long id) {
        Student student = studentService.getById(id);
        boolean flag = student != null;
        return Result.result(flag ? 200 : 404, flag, flag ? null : "未找到该学生的信息 -_-", student);
    }

    //获取 学生的毕业手续 所有状态的 完成和未完成人数
    @GetMapping("/statistic")
    public Result statistic() {
        List<Status> statuses = studentService.queryGlobalStatus();
        boolean flag = !statuses.isEmpty();
        return Result.result(flag ? 200 : 500, flag, null, statuses);
    }

    //导出学生信息
    @GetMapping("/export")
    public Result StudentDataExport(String fileName, String sheetName) {
        return fileUtil.exportDataToExcel(studentService.queryAll(), fileName, sheetName);
    }


    /**
     * *********************************** 项目无关的方法 ***************************************
     */
    //修改 学生信息(仅此)
    //无权修改学生的权限信息，由RoleController统一控制
    //无权修改学生的用户信息，由UserController统一控制
    @PreAuthorize("hasRole('admin')")
    @PostMapping("/update")
    public Result updateStatus(@RequestBody Student student) {
        boolean flag = studentService.updateById(student);
        return Result.result(flag ? 200 : 500, flag, flag ? "修改成功 ^_^" : "修改失败 -_-", null);
    }

    //添加 学生信息：同时为学生开通user账户 设置相应的权限
    @PreAuthorize("hasRole('admin')")
    @PutMapping("/save")
    public Result save(@RequestBody @Validated Student student, BindingResult result) {
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

