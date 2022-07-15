package com.max.back.neusoft.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("根据姓名查找")
@Data
public class NameFrom {
    @ApiModelProperty("查找姓名")
    private String name;
}
