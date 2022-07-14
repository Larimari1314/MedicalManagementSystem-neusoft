package com.max.back.neusoft.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@ApiModel("医生表查询")
@Data
public class DoctorFindFrom {
    @ApiModelProperty("页码")
    @NotNull
    private Integer page;
    @ApiModelProperty("姓名")
    private String name;
    @ApiModelProperty("科室id")
    private String department;
    @ApiModelProperty("挂号级别")
    private String registered;
}
