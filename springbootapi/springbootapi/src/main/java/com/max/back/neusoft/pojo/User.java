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
    @TableId
    private String id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 用户生日
     */
    private Date birthday;

    /**
     * 用户身份证号码
     */
    private String identitynumber;

    /**
     * 用户头像
     */
    private String avatar;

    /**
     * 用户性别
     */
    private String sex;

    /**
     * 逻辑删除
     */
    private Integer delete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}