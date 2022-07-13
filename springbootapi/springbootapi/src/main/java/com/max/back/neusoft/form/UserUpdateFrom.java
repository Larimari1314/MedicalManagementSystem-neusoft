package com.max.back.neusoft.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
@ApiModel("用户更新")
public class UserUpdateFrom {
    @ApiModelProperty("用户id")
    private String id;
    @ApiModelProperty("用户头像路径")
    private String avatar;
    @ApiModelProperty("用户姓名")
    @NotBlank
    private String username;
    @ApiModelProperty("性别")
    @NotBlank
    private String sex;
    @ApiModelProperty("身份证号")
    @NotBlank
    private String identitynumber;
    @ApiModelProperty("出生日期")
    private Date birthday;
    @ApiModelProperty("用户年龄")
    private int age;
}
