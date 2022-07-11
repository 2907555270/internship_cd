package com.txy.graduate.domain.sys;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

@Data
@TableName(value = "sys_user")
public class SysUser implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField(value = "username")
    private String username;

    @TableField(value = "password")
    private String password;

    @TableField(value = "avatar")
    private String avatar;

    @TableField(value = "email")
    private String email;

    @TableField(value = "city")
    private String city;

    @TableField(value = "created")
    private Date created;

    @TableField(value = "updated")
    private Date updated;

    @TableField(value = "last_login")
    private Date lastLogin;

    @TableField(value = "status")
    private Integer status;
}
