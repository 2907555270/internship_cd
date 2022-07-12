package com.txy.graduate.domain.sys;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
* 用户信息表（账号状态默认为1）
*/
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_user")
public class SysUser {
    /**
    * 主键Id
    */
    @TableField("id")
    private Long id;

    /**
    * 用户名
    */
    @TableField("username")
    @NotBlank(message = "用户名不能为空或空字符串")
    private String username;

    /**
    * 密码
    */
    @TableField("password")
    @NotBlank(message = "密码不能为空或空字符串")
    private String password;

    /**
    * 头像Url
    */
    @TableField("avatar")
    private String avatar;

    /**
    * 邮箱
    */
    @TableField("email")
    private String email;

    /**
    * 所在城市
    */
    private String city;

    /**
    * 创建时间
    */
    private Date created;

    /**
    * 更新时间
    */
    private Date updated;

    /**
    * 上次登录时间
    */
    private Date lastLogin;

    /**
    * 账号状态
    */
    private Integer status;
}