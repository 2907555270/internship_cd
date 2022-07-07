package com.txy.graduate.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Role {
    @TableId
    private int roleId;
    private String roleName;
    @TableField("createtime")
    private Date createTime;
}
