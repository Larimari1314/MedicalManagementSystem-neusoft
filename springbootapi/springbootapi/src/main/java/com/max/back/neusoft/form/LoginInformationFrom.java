package com.max.back.neusoft.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@ApiModel("修改登陆信息")
@Data
public class LoginInformationFrom {
    @ApiModelProperty("用户名")
    @NotBlank
    private String username;
    @ApiModelProperty("密码")
    @NotBlank
    private String password;

    @ApiModelProperty("用户头像")
    private String avatar;
    @ApiModelProperty("用户id")
    @NotBlank
    private String id;
}
