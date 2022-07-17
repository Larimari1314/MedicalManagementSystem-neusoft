package com.max.back.neusoft.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@ApiModel("支付信息展示")
@Data
public class PayFrom {
    @ApiModelProperty("订单id")
    @NotBlank
    private String id;

    @ApiModelProperty("totalPrice")
    @NotNull
    private Integer totalPrice;

}
