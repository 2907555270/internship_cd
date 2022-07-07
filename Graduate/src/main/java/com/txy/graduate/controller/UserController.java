package com.txy.graduate.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.txy.graduate.config.Result;
import com.txy.graduate.domain.User;
import com.txy.graduate.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;


@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserService userService;

    //分页查询所有数据
    @GetMapping("{currentPage}/{pageSize}")
    public Result findAll(@PathVariable int currentPage,@PathVariable int pageSize){
        //执行查询操作，获取数据
        IPage<User> page = userService.findAllByPage(currentPage, pageSize);
        Boolean flag = page.getSize()>0;
        //返回操作结果
        return new Result(flag,page,flag?null:"未获取到任何数据 -_-");
    }

    //登录
    @PostMapping("login")
    public Result login(@RequestBody User user, HttpSession session){
        User login = userService.login(user);
        Boolean flag = login!=null;
        //登录成功，将用户数据写入session
        if(flag)
            session.setAttribute("login_user",login);
        //返回登录结果
        return new Result(flag,login,flag?"登录成功 ^_^":"用户名或密码错误 -_-");
    }

    //退出登录
    @GetMapping("logout")
    public Result logout(HttpSession session){
        //清除会话中的数据
        session.removeAttribute("login_user");
        //将当前会话结束
        session.invalidate();
        //返回操作结果
        return new Result(true,null,"账户已退出!");
    }
}
