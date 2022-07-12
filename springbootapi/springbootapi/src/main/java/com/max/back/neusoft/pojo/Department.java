package com.max.back.neusoft.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName n_department
 */
@TableName(value ="n_department")
@Data
public class Department implements Serializable {
    /**
     * 科室编码
     */
    @TableId
    private String id;

    /**
     * 科室图片
     */
    private String avatar;

    /**
     * 科室分类
     */
    private String classification;

    /**
     * 科室名称
     */
    private String name;

    /**
     * 科室介绍
     */
    private String introduce;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}