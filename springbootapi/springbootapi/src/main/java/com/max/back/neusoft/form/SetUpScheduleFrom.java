package com.max.back.neusoft.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@ApiModel("设置班次规则")
@Data
public class SetUpScheduleFrom {
    @ApiModelProperty("设置的医生性别")
    private String sex;
    @ApiModelProperty("科室")
    private String department;
    @ApiModelProperty("起始年龄")
    private Integer startAge;
    @ApiModelProperty("终止年龄")
    private Integer endAge;
    @ApiModelProperty("排版规则")
    @NotBlank
    private String scheduleRule;
    @ApiModelProperty("挂号级别")
    private String registered;
}
