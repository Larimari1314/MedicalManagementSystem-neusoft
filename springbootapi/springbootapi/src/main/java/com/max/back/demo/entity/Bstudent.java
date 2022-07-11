package com.max.back.demo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * <p>
 *
 * </p>
 *
 * @author max
 * @since 2021-07-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("bstudent")
public class Bstudent implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id")
    private String id;

    @TableField("stucode")
    private String stucode;

    @TableField("stuname")
    private String stuname;

    @TableField("stuschid")
    private String stuschid;

    @TableField("stuage")
    private Integer stuage;

    @TableField("stubrithday")
    private LocalDate stubrithday;

    @TableField("codenameid")
    private String codenameid;
    @TableField("is_delete")
    @TableLogic
    private Integer delete;
}
