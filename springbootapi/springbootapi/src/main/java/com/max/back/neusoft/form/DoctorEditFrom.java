package com.max.back.neusoft.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@ApiModel("医生更新列表")
@Data
public class DoctorEditFrom {
    @ApiModelProperty("医生id")
    @NotBlank
    private String id;
    @ApiModelProperty("医生班次")
    @NotBlank
    private String schedule;
    @ApiModelProperty("医生所在科室")
    @NotBlank
    private String department;
    @ApiModelProperty("医生姓名")
    @NotBlank
    private String name;
    @ApiModelProperty("医生年龄")
    @NotNull
    private Integer age;
}
