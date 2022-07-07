package com.txy.graduate.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Statistic {
    private String status;
    private Integer completed;
    private Integer unfinished;
}
