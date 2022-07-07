package com.txy.graduate.config;

import com.txy.graduate.domain.Student;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentPage {
    private Integer currentPage;
    private Integer pageSize;
    private Student student;
}
