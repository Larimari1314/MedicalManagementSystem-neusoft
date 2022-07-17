package com.max.back.neusoft.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("发药界面接收")
@Data
public class DispensingMedicineFrom {
    @ApiModelProperty("订单id")
    private String id;
    @ApiModelProperty("页码")
    private Integer page;

    @ApiModelProperty("患者姓名")
    private String username;
    @ApiModelProperty("医生姓名")
    private String doctorName;
    @ApiModelProperty("当前状态")
    private String state;
}
