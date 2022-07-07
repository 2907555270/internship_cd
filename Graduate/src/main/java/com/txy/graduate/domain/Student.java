package com.txy.graduate.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {
    @TableId
    private String studentId;
    private String studentName;
    private String studentDep;
    private String studentPre;
    private String studentClass;
    private Integer studentStatus;
    private String note;

}
