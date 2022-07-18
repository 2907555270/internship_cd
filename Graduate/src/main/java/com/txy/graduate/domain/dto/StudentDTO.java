package com.txy.graduate.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.txy.graduate.config.Page;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StudentDTO implements Serializable {
    private String studentId;
    private String studentName;
    private String studentDep;
    private String studentPre;
    private String studentClass;
    private String finnishStatus;

    private Page<StudentDTO> page;
}
