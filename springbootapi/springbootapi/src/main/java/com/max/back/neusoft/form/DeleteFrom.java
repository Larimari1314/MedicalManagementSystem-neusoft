package com.max.back.neusoft.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

@ApiModel("根据id")
@Data
public class DeleteFrom implements Serializable {
    @ApiModelProperty("删除列表")
    @Size(min = 1)
//    private String[] ids;
    private List<String> ids;
}
