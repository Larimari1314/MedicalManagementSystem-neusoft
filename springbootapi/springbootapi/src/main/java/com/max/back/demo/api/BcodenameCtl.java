package com.max.back.demo.api;

import com.max.back.common.http.ResponseResult;
import com.max.back.common.sercurity.anno.AuthIgrone;
import com.max.back.demo.entity.Bcodename;
import com.max.back.demo.form.CodenameForm;
import com.max.back.demo.service.BcodenameSer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author max
 * @since 2021-07-29
 */
//@Authent
@CrossOrigin
@RestController
@RequestMapping("/api/bcodename")
public class BcodenameCtl {
    @Autowired
    private BcodenameSer ser;
    @AuthIgrone
    @PostMapping("/getcodelist")
    public ResponseResult<List<Bcodename>> getCodeList(@RequestBody @Valid CodenameForm form) {
        List<Bcodename> list = ser.queryByCodeType(form.getCodeId());
        return ResponseResult.getSuccessResult(list);
    }
}

