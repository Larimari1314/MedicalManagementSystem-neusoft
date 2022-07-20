package com.max.back.neusoft.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.max.back.common.http.ResponseResult;
import com.max.back.neusoft.dao.DoctorMapper;
import com.max.back.neusoft.form.DoctorFindFrom;
import com.max.back.neusoft.form.DoctorRegisteredFrom;
import com.max.back.neusoft.pojo.Doctor;
import com.max.back.neusoft.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author 86155
 * @description 针对表【n_doctor】的数据库操作Service实现
 * @createDate 2022-07-12 11:41:46
 */
@Service
public class DoctorServiceImpl extends ServiceImpl<DoctorMapper, Doctor>
        implements DoctorService {
    @Autowired
    private DoctorMapper doctorMapper;

    @Override
    public String findByRegis(DoctorRegisteredFrom doctorRegisteredFrom) {
        PageHelper.startPage(doctorRegisteredFrom.getPage(), 10);
        List<Map<String, Object>> byRegis = doctorMapper.findByRegis(doctorRegisteredFrom);
        PageInfo pageInfo = new PageInfo(byRegis);
        return JSON.toJSONString(ResponseResult.getSuccessResult(pageInfo, "C200", null), SerializerFeature.DisableCircularReferenceDetect);
    }

    @Override
    public String findByRegistered(DoctorFindFrom doctorFindFrom) {
        PageHelper.startPage(doctorFindFrom.getPage(), 20);
        List<Map<String, Object>> byRegis = doctorMapper.findDoctor(doctorFindFrom);
        PageInfo pageInfo = new PageInfo(byRegis);
        return JSON.toJSONString(ResponseResult.getSuccessResult(pageInfo, "C200", null), SerializerFeature.DisableCircularReferenceDetect);
    }

    @Override
    public String checkDeleteDoctor() {
        List<Map<String, Object>> maps = doctorMapper.checkDeleteDoctor();
        return JSON.toJSONString(ResponseResult.getSuccessResult(maps, "C200", null), SerializerFeature.DisableCircularReferenceDetect);
    }

    @Override
    public String deletePermanently(String id) {
        Integer integer = doctorMapper.deletePermanently(id);
        if(integer!=0){
            return JSON.toJSONString(ResponseResult.getSuccessResult(null, "C200", null), SerializerFeature.DisableCircularReferenceDetect);
        }else {
            return JSON.toJSONString(ResponseResult.getErrorResult("C500"));
        }
    }

    @Override
    public String dataRecovery(String id) {
        Integer integer = doctorMapper.dataRecovery(id);
        if(integer!=0){
            return JSON.toJSONString(ResponseResult.getSuccessResult(null, "C200", null), SerializerFeature.DisableCircularReferenceDetect);
        }else {
            return JSON.toJSONString(ResponseResult.getErrorResult("C500"));
        }
    }

    @Override
    public String getRegisDoctor() {
        List<Map<String, Object>> regisDoctor = doctorMapper.getRegisDoctor();
        System.out.println(regisDoctor);
        return JSON.toJSONString(regisDoctor);
    }
}




