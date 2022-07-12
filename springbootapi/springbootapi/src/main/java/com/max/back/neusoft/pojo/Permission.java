package com.max.back.neusoft.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName n_permission
 */
@TableName(value ="n_permission")
@Data
public class Permission implements Serializable {
    /**
     * 权限id
     */
    @TableId(value = "p_id")
    private Integer id;

    /**
     * 访问url
     */
    @TableField("p_url")
    private String url;

    /**
     * 访问boolean
     */
    @TableField("p_permission")
    private Integer permission;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}