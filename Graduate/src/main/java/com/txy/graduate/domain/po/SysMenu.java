package com.txy.graduate.domain.po;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
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
    @ExcelIgnore
    private Long id;

    /**
     * 父菜单ID，一级菜单为0
     */
    @TableField(value = "parent_id")
    @ExcelIgnore
    private Long parentId;

    @TableField(value = "name")
    @NotBlank(message = "菜单名称:name不能为空")
    @ExcelProperty("菜单名")
    private String name;

    /**
     * 菜单URL
     */
    @TableField(value = "path")
    @ExcelIgnore
    private String path;

    /**
     * 授权(多个用逗号分隔，如：user:list,user:create)
     */
    @TableField(value = "perms")
    @NotBlank(message = "菜单授权信息:perms不能为空")
    @ExcelProperty("权限信息")
    private String perms;

    @TableField(value = "component")
    @ExcelProperty("菜单描述")
    private String component;

    /**
     * 类型     0：目录   1：菜单   2：按钮
     */
    @TableField(value = "type")
    @NotNull(message = "菜单点击类型:type不能为空")
    @ExcelProperty("菜单类型")
    private Integer type;

    /**
     * 菜单图标
     */
    @TableField(value = "icon")
    @ExcelIgnore
    private String icon;

    /**
     * 排序
     */
    @TableField(value = "orderNum")
    @ExcelIgnore
    private Integer orderNum;

    @TableField(value = "created")
    @ExcelIgnore
    private Date created;

    @TableField(value = "updated")
    @ExcelIgnore
    private Date updated;

    @TableField(value = "status")
    @ExcelProperty("状态")
    private Integer status;

    @TableField(exist = false)
    @ExcelIgnore
    private List<SysMenu> children;
}