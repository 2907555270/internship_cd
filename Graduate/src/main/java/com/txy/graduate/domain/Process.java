package com.txy.graduate.domain;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Process {

    @TableId
    private int id;
    private String title;
    private Date startTime;
    private Date endTime;
    private String place;
    private String phone;
    private String img;
    private String note;

}