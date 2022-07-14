package com.max.back.neusoft.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.max.back.common.http.ResponseResult;
import com.max.back.neusoft.form.DeleteFrom;
import com.max.back.neusoft.form.DoctorEditFrom;
import com.max.back.neusoft.form.DoctorFindFrom;
import com.max.back.neusoft.form.DoctorRegisteredFrom;
import com.max.back.neusoft.pojo.Doctor;
import com.max.back.neusoft.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/doctor")
public class DoctorServlet {
    @Autowired
    private DoctorService doctorService;
    @PostMapping("/findByRegistered")
    public String findByRegistered(@RequestBody DoctorRegisteredFrom doctorRegisteredFrom) {
        return  doctorService.findByRegis(doctorRegisteredFrom);
    }
    @PostMapping("/findAllDoctor")
    public String findAllDoctor(@RequestBody @Valid DoctorFindFrom doctorFindFrom){
        return doctorService.findByRegistered(doctorFindFrom);
    }
    @PostMapping("/deleteByIds")
    public String deleteById(@RequestBody @Valid DeleteFrom params) {
        boolean delete = doctorService.removeByIds(params.getIds());
        if (delete) {
            return JSON.toJSONString(ResponseResult.getSuccessResult(null, "C200", null), SerializerFeature.DisableCircularReferenceDetect);
        } else {
            return JSON.toJSONString(ResponseResult.getErrorResult("C500"), SerializerFeature.DisableCircularReferenceDetect);
        }
    }
    @PostMapping("/addDoctor")
    public String addDoctor(@RequestBody @Valid DoctorEditFrom doctorEditFrom) {
        UpdateWrapper<Doctor> updateWrapper=new UpdateWrapper<>();
        updateWrapper.eq("d_id", doctorEditFrom.getId())
                .set("d_schedule",doctorEditFrom.getSchedule())
                .set("d_department",doctorEditFrom.getDepartment())
                .set("d_name",doctorEditFrom.getName())
                .set("d_age",doctorEditFrom.getAge());
        boolean update = doctorService.update(updateWrapper);
        if (update) {
            return JSON.toJSONString(ResponseResult.getSuccessResult(null, "C200", null), SerializerFeature.DisableCircularReferenceDetect);
        } else {
            return JSON.toJSONString(ResponseResult.getErrorResult("C500"), SerializerFeature.DisableCircularReferenceDetect);
        }
    }

}
