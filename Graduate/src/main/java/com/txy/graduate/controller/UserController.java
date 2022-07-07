package com.txy.graduate.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.txy.graduate.config.Result;
import com.txy.graduate.domain.User;
import com.txy.graduate.service.RoleService;
import com.txy.graduate.service.UserService;
import com.txy.graduate.util.QueryWrapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;


@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

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

/**
 ************************************ 带业务逻辑增删改查相关接口 ***************************************
 */
    //注册 添加新的用户--同时为用户设置权限
    @PutMapping("registry")
    public Result registry(@RequestBody Map<String,Object> map) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        //获取权限id
        Integer  role_id = (Integer) map.get("roleId");
        //将map封装为obj
        User user = QueryWrapperUtil.map2obj(map, User.class);

        //添加用户信息
        boolean flag1 = userService.save(user);
        //配置权限信息
        boolean flag2 = roleService.saveUserAndRole(user.getUserId(), role_id);

        return new Result(flag1&&flag2,null,flag1&&flag2?"添加成功 ^_^":"添加失败 -_-");
    }


    //删除 根据user_id删除用户数据--同时删除用户之前设置的权限
    @DeleteMapping("delete/{user_id}")
    public Result deleteByUId(@PathVariable String user_id){
        //删除权限信息
        boolean flag1 = roleService.removeUserAndRoleByUId(user_id);

        //删除用户信息
        boolean flag2 = userService.removeById(user_id);

        return new Result(flag1&&flag2,null,flag1&&flag2?"删除成功 ^_^":"删除失败 -_-");
    }

/**
 ************************************ 简单增删改查相关接口 ***************************************
 */

    //注册 单独添加新的用户--不为用户设置权限
    @PutMapping()
    public Result save(@RequestBody User user){
        boolean flag = userService.save(user);
        return new Result(flag,null,flag?"添加成功 ^_^":"添加失败 -_-");
    }

    //修改 根据id查找修改数据
    @PostMapping()
    public Result updateById(@RequestBody User user){
        boolean flag = userService.updateById(user);
        return new Result(flag,null,flag?"修改成功 ^_^":"修改失败 -_-");
    }

    ////删除 根据id删除用户数据--不删除用户的权限信息
    //@DeleteMapping("{user_id}")
    //public Result deleteById(@PathVariable String user_id){
    //    boolean flag = userService.removeById(user_id);
    //    return new Result(flag,null,flag?"删除成功 ^_^":"删除 失败-_-");
    //}
}
