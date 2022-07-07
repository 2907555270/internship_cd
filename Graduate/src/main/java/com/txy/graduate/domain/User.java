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
public class User {
    @TableId
    private String userId;
    private String userName;
    private String userPwd;
    private String cardNum;
    @TableField("createtime")
    private Date createTime;

}
