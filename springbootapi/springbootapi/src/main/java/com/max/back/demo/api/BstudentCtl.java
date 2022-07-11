package com.max.back.demo.api;

import cn.hutool.core.bean.BeanUtil;
import com.github.pagehelper.PageInfo;
import com.max.back.common.http.ResponseResult;
import com.max.back.common.sercurity.utils.TimeUtils;
import com.max.back.demo.entity.Bstudent;
import com.max.back.demo.form.StudentDeleteForm;
import com.max.back.demo.form.StudentForm;
import com.max.back.demo.form.StudentSaveForm;
import com.max.back.demo.service.BstudentSer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Map;

;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author max
 * @since 2021-07-29
 */
@CrossOrigin
@RestController
@RequestMapping("/api/bstudent")
public class BstudentCtl {
    @Autowired
    private BstudentSer ser;

    @PostMapping("/getstudentpagerlist")
    public ResponseResult<PageInfo<Map<String, Object>>> getStudentPagerList(@RequestBody @Valid StudentForm form) {
        PageInfo<Map<String, Object>> pager = ser.queryPagerByCond(form.getStuName(),
                form.getSchId(),
                form.getSexCode(),
                form.getPageNum(),
                form.getPageSize());
        return ResponseResult.getSuccessResult(pager);
    }

    @PostMapping("/delete")
    public ResponseResult<Boolean> delete(@RequestBody @Valid StudentDeleteForm form) {
        boolean res = ser.removeByIds(form.getIds());
        if (res) {
            return ResponseResult.getSuccessResult(true, "A004", null);
        } else {
            return ResponseResult.getMessageResult(false, "A005");
        }
    }

    @PostMapping("/add")
    public ResponseResult<Boolean> add(@RequestBody @Validated({StudentSaveForm.Base.class}) StudentSaveForm form) {
        LocalDate beginDateTime = LocalDate.parse(TimeUtils.interceptTime(form.getStubrithdaystr()), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        form.setStuage(new Date().getYear()-beginDateTime.getYear());
        form.setStubrithday(beginDateTime);
        LocalDate today = LocalDate.now();
        long duration = ChronoUnit.YEARS.between(beginDateTime, today);
        form.setStuage((int) duration);
        Bstudent student = BeanUtil.toBean(form, Bstudent.class);
        boolean res = ser.checkAndInsert(student);
        if (res) {
            return ResponseResult.getSuccessResult(true, "A006", null);
        } else {
            return ResponseResult.getMessageResult(false, "A007");
        }
    }

    @PostMapping("/findbyid")
    public ResponseResult<Bstudent> findByid(@RequestBody @Validated({StudentSaveForm.FindbyId.class}) StudentSaveForm form) {
        Bstudent bstudent = ser.getById(form.getId());
        return ResponseResult.getSuccessResult(bstudent);
    }

    @PostMapping("/update")
    public ResponseResult<Boolean> update(@RequestBody @Validated({StudentSaveForm.Update.class}) StudentSaveForm form) {
        Bstudent student = BeanUtil.toBean(form, Bstudent.class);
        boolean res = ser.updateById(student);
        if (res) {
            return ResponseResult.getSuccessResult(true, "A008", null);
        } else {
            return ResponseResult.getMessageResult(false, "A009");
        }
    }


//    @AuthIgrone
//    @GetMapping("/myget")
//    public ResponseResult<List<Bstudent>> myget() {
//        return ResponseResult.getSuccessResult(ser.myget());
//    }
}

