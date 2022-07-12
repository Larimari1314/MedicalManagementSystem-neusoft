package com.max.back.neusoft.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
    @TableId
    private String id;

    /**
     * 医生姓名
     */
    private String name;

    /**
     * 医生年龄
     */
    private Integer age;

    /**
     * 医生性别
     */
    private String sex;

    /**
     * 医生排班规则
     */
    private String schedule;

    /**
     * 医生所在科室
     */
    private String department;

    /**
     * 挂号级别
     */
    private String regis;

    /**
     * 逻辑删除
     */
    private Integer delete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}