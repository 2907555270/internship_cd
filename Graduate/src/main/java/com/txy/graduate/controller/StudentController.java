package com.txy.graduate.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.txy.graduate.config.Result;
import com.txy.graduate.domain.Student;

import com.txy.graduate.service.StudentService;
import com.txy.graduate.util.QueryWrapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    /**
     ************************************ 查询相关接口 ***************************************
     */
    //获取 分页查询所有数据
    @GetMapping("{currentPage}/{pageSize}")
    public Result findAll(@PathVariable int currentPage, @PathVariable int pageSize) {
        Map<String, Object> map = QueryWrapperUtil.getMapFromPage(currentPage, pageSize);
        IPage<Student> pageInfo = studentService.queryStudent(map);
        boolean flag = pageInfo.getTotal() > 0;
        return Result.result(flag, pageInfo, flag ? null : "未获取到任何数据 -_-");
    }

    //获取 按多种条件组合查询所有数据
    @PostMapping("select")
    public Result findByConditions(@RequestBody Map<String,Object> map) {
        IPage<Student> pageInfo = studentService.queryStudent(map);
        boolean flag = pageInfo.getTotal() > 0;
        return Result.result(flag, pageInfo, flag ? "数据查询成功 ^_^" : "未查询到任何信息 -_-");
    }


    //获取 根据输入框实时提示：单个输入，可能是学号，也可能是姓名查询学生信息
    @GetMapping("search/{content}")
    public Result searchTip(@PathVariable String content){
        List<Student> students = studentService.queryByIdOrName(content);
        return Result.result(students.size()>0,students,null);
    }

    //获取 根据id获取单个学生的数据
    @GetMapping("one/{id}")
    public Result findById(@PathVariable Long id){
        Student student = studentService.getById(id);
        boolean flag = student!=null;
        return Result.result(flag,student,flag?null:"该学生信息丢失 -_-");
    }

    /**
     ************************************ 状态数统计相关接口 ***************************************
     */

    //获取 学生的毕业手续 所有状态的 完成和未完成人数
    @GetMapping("statistic")
    public Result statistic() {
        return Result.result(true, studentService.queryGlobalStatus(),null);
    }


    /**
     ************************************ 增删改相关接口 ***************************************
     */
    //添加 学生信息：同时为学生开通user账户
    @PutMapping()
    public Result save(@RequestBody @Validated Student student, BindingResult result){
        //student数据验证
        List<FieldError> fieldErrors = result.getFieldErrors();
        if(!fieldErrors.isEmpty()){
            return Result.result(false,null,fieldErrors.get(0).getDefaultMessage());
        }

        //学号重复验证
        if(studentService.queryStudentById(student.getStudentId())!=null)
            return Result.result(false,null,"添加失败,当前学号已存在 -_-");

        //添加学生student信息
        boolean flag = studentService.save(student);

        return Result.result(flag,null,flag?"添加成功 ^_^":"添加失败 -_-");
    }

    //删除 学生信息：同时删除学生user账户以及学生的权限信息
    @DeleteMapping("{id}")
    public Result deleteById(@PathVariable Long id){
        //删除学生student信息
        boolean flag = studentService.removeById(id);

        return Result.result(flag,null,flag?"删除成功 ^_^":"删除失败 -_-");
    }

    //修改 学生信息
    @PostMapping()
    public Result updateStatus(@RequestBody Student student){
        boolean flag = studentService.updateById(student);
        return Result.result(flag,null,flag?"修改成功 ^_^":"修改失败 -_-");
    }
}

