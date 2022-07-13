package com.max.back.neusoft.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@ApiModel("用户查找表")
@Data
public class UserFindFrom {
    @ApiModelProperty("当前页码")
    @NotNull
    private Integer page;
    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("性别")
    private String sex;
}
