package com.max.back.neusoft.pojo;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName n_nonDrug
 */
@TableName(value ="n_nonDrug")
@Data
public class Nondrug implements Serializable {
    /**
     * 药品id
     */
    @TableId(value = "d_id")
    private String id;

    /**
     * 药品封面
     */
    @TableField("d_cover")
    private String cover;

    /**
     * 药品名称
     */
    @TableField("d_name")
    private String name;

    /**
     * 药品规格
     */
    @TableField("d_specification")
    private String specification;

    /**
     * 药品数量
     */
    @TableField("d_number")
    private Integer number;

    /**
     * 药品单价
     */
    @TableField("d_price")
    private Integer price;

    /**
     * 逻辑删除
     */
    @TableField("d_delete")
    @TableLogic
    private Integer delete;

    /**
     * 对外启用
     */
    @TableField("d_enable")
    private Integer enable;

    /**
     * 乐观锁
     */
    @TableField("d_version")
    private Integer version;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}