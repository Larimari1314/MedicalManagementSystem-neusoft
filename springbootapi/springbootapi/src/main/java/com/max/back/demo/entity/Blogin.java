package com.max.back.demo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author max
 * @since 2021-07-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("blogin")
public class Blogin implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId("id")
    private String id;

    @TableField("username")
    private String username;

    @TableField("password")
    private String password;

    @TableField("avatar")
    private String avatar;


}
