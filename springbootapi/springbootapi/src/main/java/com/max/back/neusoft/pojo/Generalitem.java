package com.max.back.neusoft.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName n_generalItem
 */
@TableName(value ="n_generalItem")
@Data
public class Generalitem implements Serializable {
    /**
     * 编码
     */
    private String coding;

    /**
     * 值
     */
    private String value;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}