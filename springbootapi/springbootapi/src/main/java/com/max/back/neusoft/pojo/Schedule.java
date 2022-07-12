package com.max.back.neusoft.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName n_schedule
 */
@TableName(value ="n_schedule")
@Data
public class Schedule implements Serializable {
    /**
     * 排班表id
     */
    @TableId(value = "s_id")
    private String id;

    /**
     * 排班名称
     */
    @TableField("s_name")
    private String name;

    /**
     * 起始时间
     */
    @TableField("s_start")
    private Date start;

    /**
     * 结束时间
     */
    @TableField("s_end")
    private Date end;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}