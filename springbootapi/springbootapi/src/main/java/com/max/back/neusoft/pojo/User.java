package com.max.back.neusoft.pojo;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * 用户表
 */
@TableName(value ="n_user")
@Data
public class User implements Serializable {
    /**
     * 用户id
     */
    @TableId(value = "u_id")
    private String id;

    /**
     * 用户名
     */
    @TableField("u_userName")
    private String username;

    /**
     * 年龄
     */
    @TableField("u_age")
    private Integer age;

    /**
     * 用户生日
     */
    @TableField("u_birthday")
    private Date birthday;

    /**
     * 用户身份证号码
     */
    @TableField("u_identityNumber")
    private String identitynumber;

    /**
     * 用户头像
     */
    @TableField("u_avatar")
    private String avatar;

    /**
     * 用户性别
     */
    @TableField("u_sex")
    private String sex;

    /**
     * 逻辑删除
     */
    @TableField("u_delete")
    @TableLogic
    private Integer deletes;
}