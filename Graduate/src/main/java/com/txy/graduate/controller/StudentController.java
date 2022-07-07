package com.txy.graduate.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.txy.graduate.config.Result;
import com.txy.graduate.config.StudentPage;
import com.txy.graduate.domain.Student;
import com.txy.graduate.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("student")
public class StudentController {
    @Autowired
    private StudentService studentService;

    //分页查询所有数据
    @GetMapping("{currentPage}/{pageSize}")
    public Result findAll(@PathVariable int currentPage, @PathVariable int pageSize) {
        IPage<Student> pageInfo = studentService.findAllByPage(currentPage, pageSize);
        Boolean flag = pageInfo.getSize() > 0;
        return new Result(flag, pageInfo, flag ? null : "未获取到任何数据 -_-");
    }

    //按多种条件组合查询所有数据
    @PostMapping("select")
    public Result findByConditions(@RequestBody StudentPage studentPage) {
        IPage<Student> pageInfo = studentService.findByConditionsAndPage(studentPage);
        Boolean flag = pageInfo.getSize() > 0;
        return new Result(flag, pageInfo, flag ? "数据查询成功 ^_^" : "未查询到任何信息 -_-");
    }

    //统计 学生的毕业手续 所有状态的 完成和未完成人数
    @GetMapping("status")
    public Result statistic() {
        return new Result(true, studentService.findGlobalStatus());
    }

    @GetMapping("one/{student_id}")
    public Result getInfo(@PathVariable String student_id){
        //return new Result();
        return null;
    }


}
