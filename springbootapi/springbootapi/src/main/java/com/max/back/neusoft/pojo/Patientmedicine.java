package com.max.back.neusoft.pojo;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import org.springframework.data.relational.core.mapping.Table;

/**
 * 
 * @TableName n_patientMedicine
 */
@TableName(value ="n_patientMedicine")
@Data
public class Patientmedicine implements Serializable {
    /**
     * 订单编号
     */
    @TableId("p_id")
    private String id;

    /**
     * 患者id
     */
    @TableField("p_userId")
    private String userid;

    /**
     * 医生id
     */
    @TableField("p_doctorId")
    private String doctorid;

    /**
     * 最后一次修改时间
     */
    @TableField("p_date")
    private Date date;

    /**
     * 当前状态(已发、未发、已退药)
     */
    @TableField("p_state")
    private String state;

    /**
     * 乐观锁
     */
    @TableField("p_version")
    private Integer version;

    /**
     * 逻辑删除
     */
    @TableField("p_delete")
    @TableLogic
    private Integer deletes;

    /**
     * 价格
     */
    @TableField("p_price")
    private Double price;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}