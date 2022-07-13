package com.txy.graduate.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Status {
    //标识标题
    private String title;

    //总人数
    private Long total;

    //完成人数
    private Long completed;

    //未完成人数
    private Long unfinished;

    //备注人数
    private Long noteNum;
}
