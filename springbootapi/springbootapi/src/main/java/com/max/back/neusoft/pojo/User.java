package com.max.back.neusoft.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName n_user
 */
@TableName(value ="n_user")
@Data
public class User implements Serializable {
    /**
     * 
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
    private Integer delete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}