package com.max.back.neusoft.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName n_drugSpecification
 */
@TableName(value ="n_drugSpecification")
@Data
public class Drugspecification implements Serializable {
    /**
     * 药品规格id
     */
    @TableId
    private String id;

    /**
     * 规格名称
     */
    private String name;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}