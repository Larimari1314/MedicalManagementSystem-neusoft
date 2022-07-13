package com.max.back.neusoft.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@ApiModel("查找科室")
@Data
public class DepartmentFindFrom {
    @ApiModelProperty(value = "当前页码")
    @NotNull
    private Integer page;
    @ApiModelProperty(value = "科室类别")
    private String departmentClassification;
    @ApiModelProperty(value = "搜索名字")
    private String name;
}
