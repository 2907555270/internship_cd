package com.txy.graduate.domain.po;

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
    private Long id;

    @TableField(value = "name")
    private String name;

    @TableField(value = "code")
    private String code;

    /**
     * 备注
     */
    @TableField(value = "remark")
    private String remark;

    @TableField(value = "created")
    private Date created;

    @TableField(value = "updated")
    private Date updated;

    @TableField(value = "status")
    private Integer status;
}