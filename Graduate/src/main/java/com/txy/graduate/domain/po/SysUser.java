package com.txy.graduate.domain.po;

import java.io.Serializable;
import java.util.Date;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
* 用户信息表（账号状态默认为1）
*/
@Data
@NoArgsConstructor
@TableName("sys_user")
@JsonInclude(value = JsonInclude.Include.NON_NULL)

public class SysUser implements Serializable {
    /**
    * 主键Id
    */
    @TableId(value = "id",type = IdType.AUTO)
    @ExcelIgnore
    private Long id;

    /**
    * 用户名
    */
    @TableField("username")
    @NotBlank(message = "用户名不能为空或空字符串")
    @ExcelProperty("用户名")
    private String username;

    /**
    * 密码
    */
    @TableField("password")
    @NotBlank(message = "密码不能为空或空字符串")
    @ExcelProperty("密码")
    private String password;

    /**
    * 头像Url
    */
    @TableField("avatar")
    @ExcelIgnore
    private String avatar;

    /**
    * 邮箱
    */
    @TableField("email")
    @ExcelProperty("邮箱")
    private String email;

    /**
    * 所在城市
    */
    @ExcelProperty("所在城市")
    private String city;

    /**
    * 创建时间
    */
    @ExcelProperty("注册时间")
    private Date created;

    /**
    * 更新时间
    */
    @ExcelIgnore
    private Date updated;

    /**
    * 上次登录时间
    */
    @ExcelIgnore
    private Date lastLogin;

    /**
    * 账号状态
    */
    @ExcelProperty("账号状态")
    private Integer status;
}