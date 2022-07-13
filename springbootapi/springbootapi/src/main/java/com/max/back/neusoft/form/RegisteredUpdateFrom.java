package com.max.back.neusoft.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@ApiModel("挂号级别更新")
@Data
public class RegisteredUpdateFrom {
    @ApiModelProperty("id")
    private String id;

    @ApiModelProperty("挂号名称编码")
    @NotBlank
    private String name;
    @ApiModelProperty("当前价格")
    @NotNull
    private Integer price;

    @ApiModelProperty("版本号")
    private Integer version;
}
