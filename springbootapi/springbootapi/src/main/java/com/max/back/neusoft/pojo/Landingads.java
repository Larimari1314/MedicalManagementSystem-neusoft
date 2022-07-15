package com.max.back.neusoft.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName n_landingAds
 */
@TableName(value ="n_landingAds")
@Data
public class Landingads implements Serializable {
    /**
     * 
     */
    @TableId
    private String id;

    /**
     * 
     */
    private String imgurl;

    /**
     * 
     */
    private String ads;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}