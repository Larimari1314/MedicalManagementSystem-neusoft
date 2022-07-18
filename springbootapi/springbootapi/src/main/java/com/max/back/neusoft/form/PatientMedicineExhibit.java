package com.max.back.neusoft.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("发药界面展示数据")
@Data
public class PatientMedicineExhibit {
    @ApiModelProperty("发药id")
    private String id;
    @ApiModelProperty("患者姓名")
    private String username;
    @ApiModelProperty("医生姓名")
    private String dname;
    @ApiModelProperty("时间")
    private String times;
    @ApiModelProperty("当前状态")
    private String state;
    @ApiModelProperty("版本号")
    private Integer version;
    @ApiModelProperty("价格")
    private Double price;
}
