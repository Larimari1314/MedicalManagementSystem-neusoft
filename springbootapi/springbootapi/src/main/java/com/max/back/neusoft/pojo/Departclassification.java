package com.max.back.neusoft.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName n_departClassification
 */
@TableName(value ="n_departClassification")
@Data
public class Departclassification implements Serializable {
    /**
     * 科室id
     */
    @TableId
    private String id;

    /**
     * 科室类别名称
     */
    private String name;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}