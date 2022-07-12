package com.max.back.neusoft.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
    @TableId
    private String id;

    /**
     * 药品封面
     */
    private String cover;

    /**
     * 药品名称
     */
    private String name;

    /**
     * 药品规格
     */
    private String specification;

    /**
     * 药品数量
     */
    private Integer number;

    /**
     * 药品单价
     */
    private Integer price;

    /**
     * 逻辑删除
     */
    private Integer delete;

    /**
     * 对外启用
     */
    private Integer enable;

    /**
     * 乐观锁
     */
    private Integer version;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}