package com.txy.graduate.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatisticVo {
    private Integer total;
    private Integer report;
    private Integer graduation;
    private Integer owe;
    private Integer book;
    private Integer archive;
    private Integer dormitory;
    private Integer diploma;
}
