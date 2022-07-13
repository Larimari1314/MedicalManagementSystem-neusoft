package com.max.back.neusoft.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@ApiModel("根据挂号信息查找医生")
@Data
public class DoctorRegisteredFrom {
    @ApiModelProperty("挂号id")
    @NotBlank
    private String regisId;

    @ApiModelProperty("查找名称")
    private String name;

    @ApiModelProperty("当前页")
    @NotNull
    private Integer page;

}
