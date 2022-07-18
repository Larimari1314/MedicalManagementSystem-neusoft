package com.max.back.neusoft.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.max.back.common.http.ResponseResult;
import com.max.back.neusoft.form.*;
import com.max.back.neusoft.pojo.Doctor;
import com.max.back.neusoft.pojo.Patientmedicine;
import com.max.back.neusoft.service.DoctorService;
import com.max.back.neusoft.service.PatientmedicineService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.concurrent.atomic.AtomicBoolean;

@CrossOrigin
@RestController
@RequestMapping("/doctor")
public class DoctorServlet {
    @Autowired
    private DoctorService doctorService;
    @Autowired
    private PatientmedicineService patientmedicineService;
    /**
     * 根据规则查找医生
     * @param doctorRegisteredFrom
     * @return
     */
    @PostMapping("/findByRegistered")
    public String findByRegistered(@RequestBody DoctorRegisteredFrom doctorRegisteredFrom) {
        return doctorService.findByRegis(doctorRegisteredFrom);
    }

    /**
     * 查找全部医生
     * @param doctorFindFrom
     * @return
     */
    @PostMapping("/findAllDoctor")
    public String findAllDoctor(@RequestBody @Valid DoctorFindFrom doctorFindFrom) {
        return doctorService.findByRegistered(doctorFindFrom);
    }

    /**
     * 删除医生
     * @param params
     * @return
     */
    @PostMapping("/deleteByIds")
    public String deleteById(@RequestBody @Valid DeleteFrom params) {
        //在订单表根据用户id查找是否存在相同用户
        AtomicBoolean same = new AtomicBoolean(false);
        params.getIds().stream().forEach(s -> {
            QueryWrapper<Patientmedicine> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("p_doctorId",s);
            if(patientmedicineService.getMap(queryWrapper)!=null){
                same.set(true);
            }
        });
        if(same.get()){
            return JSON.toJSONString(ResponseResult.getErrorResult("C405"));
        }
        boolean delete = doctorService.removeByIds(params.getIds());
        if (delete) {
            return JSON.toJSONString(ResponseResult.getSuccessResult(null, "C200", null), SerializerFeature.DisableCircularReferenceDetect);
        } else {
            return JSON.toJSONString(ResponseResult.getErrorResult("C500"), SerializerFeature.DisableCircularReferenceDetect);
        }
    }

    /**
     * 添加医生
     * @param doctorEditFrom
     * @return
     */
    @PostMapping("/addDoctor")
    public String addDoctor(@RequestBody @Valid DoctorEditFrom doctorEditFrom) {
        UpdateWrapper<Doctor> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("d_id", doctorEditFrom.getId())
                .set("d_schedule", doctorEditFrom.getSchedule())
                .set("d_department", doctorEditFrom.getDepartment())
                .set("d_name", doctorEditFrom.getName())
                .set("d_age", doctorEditFrom.getAge());
        boolean update = doctorService.update(updateWrapper);
        if (update) {
            return JSON.toJSONString(ResponseResult.getSuccessResult(null, "C200", null), SerializerFeature.DisableCircularReferenceDetect);
        } else {
            return JSON.toJSONString(ResponseResult.getErrorResult("C500"), SerializerFeature.DisableCircularReferenceDetect);
        }
    }

    /**
     * 更新医生信息
     * @param setUpScheduleFrom
     * @return
     */
    @PostMapping("/setUpSchedule")
    public String setUpSchedule(@RequestBody @Valid SetUpScheduleFrom setUpScheduleFrom) {
        if (setUpScheduleFrom.getEndAge() == null) {
            setUpScheduleFrom.setEndAge(100);
        }
        if (setUpScheduleFrom.getStartAge() == null) {
            setUpScheduleFrom.setStartAge(0);
        }
        //更新
        UpdateWrapper<Doctor> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq(StringUtils.isNotBlank(setUpScheduleFrom.getDepartment()), "d_department", setUpScheduleFrom.getDepartment())
                .eq(StringUtils.isNotBlank(setUpScheduleFrom.getRegistered()), "d_regis", setUpScheduleFrom.getScheduleRule())
                .between("d_age", setUpScheduleFrom.getStartAge(), setUpScheduleFrom.getEndAge())
                .set("d_schedule", setUpScheduleFrom.getScheduleRule());
        boolean update = doctorService.update(updateWrapper);
        if (update) {
            return JSON.toJSONString(ResponseResult.getSuccessResult(null, "C200", null), SerializerFeature.DisableCircularReferenceDetect);
        } else {
            return JSON.toJSONString(ResponseResult.getErrorResult("C500"), SerializerFeature.DisableCircularReferenceDetect);
        }
    }

    @GetMapping("/checkDeleteDoctor")
    public String checkDeleteDoctor() {
        return doctorService.checkDeleteDoctor();
    }

    /**
     * 永久删除
     * @param id
     * @return
     */
    @PostMapping("/deletePermanently")
    public String deletePermanently(@RequestBody @NotBlank String id) {
        id = id.replace("=", "");
        return doctorService.deletePermanently(id);
    }

    /**
     * 恢复数据
     * @param id
     * @return
     */
    @PostMapping("/dataRecovery")
    public String dataRecovery(@RequestBody @NotBlank String id) {
        id = id.replace("=", "");
        return doctorService.dataRecovery(id);
    }

}
