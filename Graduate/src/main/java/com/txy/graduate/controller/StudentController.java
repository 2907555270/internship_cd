package com.txy.graduate.controller;

import com.txy.graduate.service.ISysRoleService;
import com.txy.graduate.service.StudentService;
import com.txy.graduate.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private ISysUserService userService;

    @Autowired
    private ISysRoleService roleService;

    ///**
    // ************************************ 查询相关接口 ***************************************
    // */
    ////获取 分页查询所有数据
    //@GetMapping("{currentPage}/{pageSize}")
    //public Result findAll(@PathVariable int currentPage, @PathVariable int pageSize) {
    //    IPage<Student> pageInfo = studentService.findAllByPage(currentPage, pageSize);
    //    Boolean flag = pageInfo.getSize() > 0;
    //    return new Result(flag, pageInfo, flag ? null : "未获取到任何数据 -_-");
    //}
    //
    ////获取 按多种条件组合查询所有数据
    //@PostMapping("select")
    //public Result findByConditions(@RequestBody StudentPage studentPage) {
    //    IPage<Student> pageInfo = studentService.findByConditionsAndPage(studentPage);
    //    Boolean flag = pageInfo.getSize() > 0;
    //    return new Result(flag, pageInfo, flag ? "数据查询成功 ^_^" : "未查询到任何信息 -_-");
    //}
    //
    ////获取 根据输入框实时提示：单个输入，可能是学号，也可能是姓名查询学生信息
    //@GetMapping("search/{content}")
    //public Result searchTip(@PathVariable String content){
    //    List<Student> students = studentService.findByIdOrName(content);
    //    return new Result(students.size()>0,students);
    //}
    //
    ////获取 根据student_id获取单个学生的数据
    //@GetMapping("one/{student_id}")
    //public Result getInfo(@PathVariable String student_id){
    //    Student student = studentService.findById(student_id);
    //    Boolean flag = student!=null;
    //    return new Result(flag,student,flag?null:"该学生信息丢失 -_-");
    //}
    //
    //
    ///**
    // ************************************ 状态数统计相关接口 ***************************************
    // */
    //
    ////获取 学生的毕业手续 所有状态的 完成和未完成人数
    //@GetMapping("status")
    //public Result statistic() {
    //    return new Result(true, studentService.findGlobalStatus());
    //}
    //
    //
    ///**
    // ************************************ 带业务逻辑的增删改查相关接口 ***************************************
    // */
    ////添加 学生信息：同时为学生开通user账户
    //@PutMapping()
    //public Result save(@RequestBody Student student){
    //
    //    //学号重复
    //    if(studentService.findById(student.getStudentId())!=null)
    //        return new Result(false,null,"添加失败,当前学号已存在 -_-");
    //
    //    //添加学生student信息
    //    boolean flag1 = studentService.save(student);
    //
    //    //根据学生信息生成User信息
    //    SysUser user = new SysUser();
    //    user.setId(Long.parseLong(student.getStudentId()));
    //    user.setPassword(student.getStudentId());
    //    user.setUsername(student.getStudentName());
    //
    //    //注册学生用户user信息
    //    boolean flag2 = userService.save(user);
    //
    //    //根据学生信息生成Role_User信息
    //    SysRole role = roleService.findByName("学生");
    //    //添加学生User对应的权限信息 user_role
    //    boolean flag3 = roleService.saveUserAndRole(user.getId(), role.getId());
    //
    //    return new Result(flag1&&flag2&&flag3,null,flag1&&flag2&&flag3?"添加成功 ^_^":"添加失败 -_-");
    //}
    //
    ////删除 学生信息：同时删除学生user账户以及学生的权限信息
    //@DeleteMapping("{student_id}")
    //public Result deleteById(@PathVariable String student_id){
    //    //删除学生student信息
    //    boolean flag1 = studentService.removeById(student_id);
    //
    //    //删除用户权限表 user_role
    //    boolean flag2 = roleService.removeUserAndRoleByUId(student_id);
    //
    //    //删除用户user信息
    //    boolean flag3 = userService.removeById(student_id);
    //
    //    return new Result(flag1&&flag2&&flag3,null,flag1&&flag2&&flag3?"删除成功 ^_^":"删除失败 -_-");
    //}
    //
    ///**
    // ************************************ 简单增删改查相关接口 ***************************************
    // */
    ////修改 学生信息
    //@PostMapping()
    //public Result updateStatus(@RequestBody Student student){
    //    Boolean flag = studentService.updateById(student);
    //    return new Result(flag,null,flag?"修改成功 ^_^":"修改失败 -_-");
    //}
}

