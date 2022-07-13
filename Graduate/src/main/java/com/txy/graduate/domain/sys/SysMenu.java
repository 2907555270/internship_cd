package com.txy.graduate.domain.sys;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@TableName(value = "sys_menu")
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class SysMenu implements Serializable {

    /**
     * 主键Id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 父菜单ID，一级菜单为0
     */
    @TableField(value = "parent_id")
    private Long parentId;

    @TableField(value = "name")
    @NotBlank(message = "菜单名称:name不能为空")
    private String name;

    /**
     * 菜单URL
     */
    @TableField(value = "path")
    private String path;

    /**
     * 授权(多个用逗号分隔，如：user:list,user:create)
     */
    @TableField(value = "perms")
    @NotBlank(message = "菜单授权信息:perms不能为空")
    private String perms;

    @TableField(value = "component")
    private String component;

    /**
     * 类型     0：目录   1：菜单   2：按钮
     */
    @TableField(value = "type")
    @NotNull(message = "菜单点击类型:type不能为空")
    private Integer type;

    /**
     * 菜单图标
     */
    @TableField(value = "icon")
    private String icon;

    /**
     * 排序
     */
    @TableField(value = "orderNum")
    private Integer orderNum;

    @TableField(value = "created")
    private Date created;

    @TableField(value = "updated")
    private Date updated;

    @TableField(value = "status")
    private Integer status;

    @TableField(exist = false)
    private List<SysMenu> children;
}