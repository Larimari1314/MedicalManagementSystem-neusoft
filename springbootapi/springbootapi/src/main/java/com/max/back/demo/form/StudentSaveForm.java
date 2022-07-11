package com.max.back.demo.form;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@Data
public class StudentSaveForm {
    public interface Base{
    }
    public interface FindbyId {
    }
    public interface Update extends Base {
    }
//    @NotNull(groups = {Update.class, FindbyId.class})
    @Length(max = 36, groups = {Update.class, FindbyId.class})
    private String id;

//    @NotNull(groups = {Update.class})
    @Length(min = 3, max = 3, groups = {Update.class})
    private String stucode;

    @NotNull(groups = {Base.class})
    @Length(min = 1, max = 30, groups = {Base.class})
    private String stuname;

    @NotNull(groups = {Base.class})
    @Length(max = 36, groups = {Base.class})
    private String stuschid;

//    @NotNull(groups = {Base.class})
    @Range(min = 1, groups = {Base.class})
    private Integer stuage;

//    @Pattern(regexp = "(\\d{1,4}-\\d{2}-\\d{2})?", groups = {Base.class})
    private String stubrithdaystr;

    private LocalDate stubrithday;

    @NotNull(groups = {Base.class})
    @Length(max = 36, groups = {Base.class})
    private String codenameid;
}
