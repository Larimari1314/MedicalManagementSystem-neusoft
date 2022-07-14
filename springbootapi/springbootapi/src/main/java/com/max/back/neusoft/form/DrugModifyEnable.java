package com.max.back.neusoft.form;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@ApiModel("修改药品状态")
@Data
public class DrugModifyEnable {
    @ApiModelProperty("药品id")
    @NotBlank
    private String id;

    @ApiModelProperty("修改状态")
    @NotNull
    private Integer enable;
}
