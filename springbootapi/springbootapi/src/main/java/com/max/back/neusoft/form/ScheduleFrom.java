package com.max.back.neusoft.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@ApiModel("排序时间表")
@Data
public class ScheduleFrom {
    @ApiModelProperty("时间id")
    @NotBlank
    private String id;
    @ApiModelProperty("名称")
    @NotBlank
    private String name;
    @ApiModelProperty("开始时间")
    @NotBlank
    private String start;
    @ApiModelProperty("结束时间")
    @NotBlank
    private String end;
}
