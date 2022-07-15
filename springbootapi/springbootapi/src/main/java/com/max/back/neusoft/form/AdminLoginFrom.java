package com.max.back.neusoft.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@ApiModel("管理员登陆表")
@Data
public class AdminLoginFrom {
    @ApiModelProperty("用户名")
    @NotBlank
    private String username;
    @ApiModelProperty("密码")
    @NotBlank
    private String password;
}
