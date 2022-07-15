package com.max.back.neusoft.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@ApiModel("修改表单项")
@Data
public class GeneralitemFrom {
    @ApiModelProperty("表单项id")
    @NotBlank
    private String coding;

    @ApiModelProperty("表单值")
    @NotBlank
    private String value;
}
