package com.max.back.neusoft.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.max.back.neusoft.dao.GeneralitemMapper;
import com.max.back.neusoft.pojo.Generalitem;
import com.max.back.neusoft.service.GeneralitemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author 86155
 * @description 针对表【n_generalItem】的数据库操作Service实现
 * @createDate 2022-07-12 11:45:29
 */
@Service
public class GeneralitemServiceImpl extends ServiceImpl<GeneralitemMapper, Generalitem>
        implements GeneralitemService {
    @Autowired
    private GeneralitemMapper generalitemMapper;

    @Override
    public String findGender() {
        List<Map<String, Object>> gender = generalitemMapper.findGender();
        return JSON.toJSONString(gender, SerializerFeature.DisableCircularReferenceDetect);
    }

    @Override
    public String findByRegistered() {
        List<Map<String, Object>> byRegistered = generalitemMapper.findByRegistered();
        return JSON.toJSONString(byRegistered,SerializerFeature.DisableCircularReferenceDetect);
    }

    @Override
    public String getMedicationStatus() {
        List<Map<String, Object>> medicationStatus = generalitemMapper.getMedicationStatus();
        return JSON.toJSONString(medicationStatus);
    }
}




