package com.txy.graduate.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StudentDTO implements Serializable {
    private String studentId;
    private String StudentName;
    private String StudentDep;
    private String StudentPre;
    private String StudentClass;
    private String StudentStatus;
}
