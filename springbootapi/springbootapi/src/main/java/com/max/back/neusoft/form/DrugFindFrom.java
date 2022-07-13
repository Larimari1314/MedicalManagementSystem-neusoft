package com.max.back.neusoft.form;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@ApiModel("药品规则查找")
@Data
public class DrugFindFrom {
    @ApiModelProperty("当前页码")
    @NotNull
    private Integer page;
    @ApiModelProperty("查找姓名")
    private String name;
    @ApiModelProperty("查找规格")
    private String specification;
}
