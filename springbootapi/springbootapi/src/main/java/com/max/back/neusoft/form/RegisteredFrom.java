package com.max.back.neusoft.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@ApiModel("页面传入搜索栏")
@Data
public class RegisteredFrom {
    @ApiModelProperty("挂号级别名称")
    private String name;
    @ApiModelProperty("当前页码")
    @NotNull
    private Integer page;
}
