package com.max.back.neusoft.pojo;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName n_doctor
 */
@TableName(value ="n_doctor")
@Data
public class Doctor implements Serializable {
    /**
     * 医生id
     */
    @TableId(value = "d_id")
    private String id;

    /**
     * 医生姓名
     */
    @TableField("d_name")
    private String name;

    /**
     * 医生年龄
     */
    @TableField("d_age")
    private Integer age;

    /**
     * 医生性别
     */
    @TableField("d_sex")
    private String sex;

    /**
     * 医生排班规则
     */
    @TableField("d_schedule")
    private String schedule;

    /**
     * 医生所在科室
     */
    @TableField("d_department")
    private String department;

    /**
     * 挂号级别
     */
    @TableField("d_regis")
    private String regis;

    /**
     * 逻辑删除
     */
    @TableField("d_delete")
    @TableLogic
    private Integer delete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}