package com.max.back.neusoft.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName n_registered
 */
@TableName(value ="n_registered")
@Data
public class Registered implements Serializable {
    /**
     * 挂号级别id
     */
    @TableId(value = "r_id")
    private String id;

    /**
     * 挂号级别
     */
    @TableField("r_name")
    private String name;

    /**
     * 挂号金额
     */
    @TableField("r_price")
    private Integer price;

    /**
     * 乐观锁
     */
    @TableField("r_version")
    private Integer version;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}