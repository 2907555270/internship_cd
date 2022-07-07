package com.txy.graduate.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.txy.graduate.config.Result;
import com.txy.graduate.config.StudentPage;
import com.txy.graduate.domain.Student;
import com.txy.graduate.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


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
        IPage<Student> pageInfo = studentService.findAllByPage(currentPage, pageSize);
        Boolean flag = pageInfo.getSize() > 0;
        return new Result(flag, pageInfo, flag ? null : "未获取到任何数据 -_-");
    }

    //获取 按多种条件组合查询所有数据
    @PostMapping("select")
    public Result findByConditions(@RequestBody StudentPage studentPage) {
        IPage<Student> pageInfo = studentService.findByConditionsAndPage(studentPage);
        Boolean flag = pageInfo.getSize() > 0;
        return new Result(flag, pageInfo, flag ? "数据查询成功 ^_^" : "未查询到任何信息 -_-");
    }

    //获取 根据输入框实时提示：单个输入，可能是学号，也可能是姓名查询学生信息
    @GetMapping("search/{content}")
    public Result searchTip(@PathVariable String content){
        List<Student> students = studentService.findByIdOrName(content);
        return new Result(students.size()>0,students);
    }

    //获取 根据student_id获取单个学生的数据
    @GetMapping("one/{student_id}")
    public Result getInfo(@PathVariable String student_id){
        Student student = studentService.findById(student_id);
        Boolean flag = student!=null;
        return new Result(flag,student,flag?null:"该学生信息丢失 -_-");
    }

    /**
     ************************************ 查询相关接口 ***************************************
     */

    //获取 学生的毕业手续 所有状态的 完成和未完成人数
    @GetMapping("status")
    public Result statistic() {
        return new Result(true, studentService.findGlobalStatus());
    }

    //修改 离校手续进度状态
    @PostMapping("status")
    public Result updateStatus(@RequestBody Student student){
        Boolean flag = studentService.updateStatus(student);
        return new Result(flag,null,flag?"操作成功 ^_^":"操作失败 -_-");
    }
}
