package com.max.back.demo.form;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class StudentDeleteForm {
    @NotNull
    @Size(min = 1)
    private List<String> ids;
}
