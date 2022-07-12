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
    @TableId
    private String id;

    /**
     * 挂号级别
     */
    private String name;

    /**
     * 挂号金额
     */
    private Integer price;

    /**
     * 乐观锁
     */
    private Integer version;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}