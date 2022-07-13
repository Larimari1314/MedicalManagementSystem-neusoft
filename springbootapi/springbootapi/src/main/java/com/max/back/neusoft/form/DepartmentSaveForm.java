package com.max.back.neusoft.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@ApiModel("科室存储")
@Data
public class DepartmentSaveForm {

    @ApiModelProperty("科室编码")
    @NotBlank
    private String id;

    @ApiModelProperty("科室分类")
    @NotBlank
    private String classification;

    @ApiModelProperty("科室名称")
    @NotBlank
    private String name;

    @ApiModelProperty("科室封面")
    private String avatar;

    @ApiModelProperty("科室介绍")
    @NotBlank
    private String introduce;

}
