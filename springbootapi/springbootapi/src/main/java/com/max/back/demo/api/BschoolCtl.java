package com.max.back.demo.api;

import com.max.back.common.http.ResponseResult;
import com.max.back.common.sercurity.anno.AuthIgrone;
import com.max.back.demo.entity.Bschool;
import com.max.back.demo.service.BschoolSer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author max
 * @since 2021-07-29
 */
//@Authent
@CrossOrigin
@RestController
@RequestMapping("/api/bschool")
public class BschoolCtl {
    @Autowired
    private BschoolSer ser;

    @AuthIgrone
    @PostMapping("/getallscllist")
    public ResponseResult<List<Bschool>> getAllSclList() {
        return ResponseResult.getSuccessResult(ser.list());
    }
}