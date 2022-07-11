package com.max.back.demo.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class CodenameForm {
    @NotEmpty
    private String codeId;
}
