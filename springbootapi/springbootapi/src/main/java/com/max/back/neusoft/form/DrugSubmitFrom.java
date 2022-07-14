package com.max.back.neusoft.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@ApiModel("药品提交表单")
@Data
public class DrugSubmitFrom {
    @ApiModelProperty("id")
    private String id;
    @ApiModelProperty("名称")
    @NotBlank
    private String name;
    @ApiModelProperty("药品规格")
    @NotBlank
    private String sname;
    @ApiModelProperty("封面")
    private String cover;
    @ApiModelProperty("数量")
    @NotNull
    private Integer number;
    @ApiModelProperty("价格")
    @NotNull
    private Integer price;

    @ApiModelProperty("版本号")
    @NotNull
    private Integer version;
}

