package com.max.back.demo.form;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class StudentForm {
    private String stuName;
    private String schId;
    private String sexCode;
    @NotNull
    @Min(1)
    private Integer pageNum;
    @NotNull
    @Range(min = 5, max = 50)
    private Integer pageSize;
}
