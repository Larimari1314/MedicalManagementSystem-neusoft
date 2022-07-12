package com.max.back.neusoft.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName n_login
 */
@TableName(value ="n_login")
@Data
public class Login implements Serializable {
    /**
     * 登录id
     */
    @TableId
    private String id;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 用户名
     */
    private String username;

    /**
     * 登陆密码
     */
    private String password;

    /**
     * 权限id
     */
    private Integer permission;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}