package com.max.back.demo.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@ApiModel(value="登录From")
@Data
public class LoginForm {
    @ApiModelProperty(value="用户名")
    @NotBlank
    @Length(max=20)
    private String userName;

    @ApiModelProperty(value="密码")
    @NotBlank
    @Length(max=20)
    private String password;

}
