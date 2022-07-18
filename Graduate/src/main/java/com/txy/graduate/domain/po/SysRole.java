package com.txy.graduate.domain.po;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@TableName(value = "sys_role")
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class SysRole implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    @ExcelIgnore
    private Long id;

    @TableField(value = "name")
    @ExcelProperty("角色名")
    private String name;

    @TableField(value = "code")
    @ExcelProperty("角色编码")
    private String code;

    /**
     * 备注
     */
    @TableField(value = "remark")
    @ExcelProperty("角色简介")
    private String remark;

    @TableField(value = "created")
    @ExcelIgnore
    private Date created;

    @TableField(value = "updated")
    @ExcelIgnore
    private Date updated;

    @TableField(value = "status")
    @ExcelProperty("角色状态")
    private Integer status;
}