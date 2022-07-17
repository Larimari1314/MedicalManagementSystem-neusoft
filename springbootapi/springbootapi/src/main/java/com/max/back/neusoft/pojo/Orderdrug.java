package com.max.back.neusoft.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName n_orderDrug
 */
@TableName(value ="n_orderDrug")
@Data
public class Orderdrug implements Serializable {
    /**
     * 订单id
     */
    @TableId(value = "o_id")
    private String id;

    /**
     * 药品表
     */
    @TableField("o_drugId")
    private String drugid;

    /**
     * 药品数量
     */
    @TableField("o_number")
    private Integer number;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}