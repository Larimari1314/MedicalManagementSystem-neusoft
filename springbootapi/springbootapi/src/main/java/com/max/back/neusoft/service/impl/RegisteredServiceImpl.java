package com.max.back.neusoft.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.max.back.common.http.ResponseResult;
import com.max.back.neusoft.form.RegisteredFrom;
import com.max.back.neusoft.form.RegisteredUpdateFrom;
import com.max.back.neusoft.pojo.Registered;
import com.max.back.neusoft.service.RegisteredService;
import com.max.back.neusoft.dao.RegisteredMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
* @author 86155
* @description 针对表【n_registered】的数据库操作Service实现
* @createDate 2022-07-12 11:52:08
*/
@Service
public class RegisteredServiceImpl extends ServiceImpl<RegisteredMapper, Registered>
    implements RegisteredService{
    @Autowired
    private RegisteredMapper registeredMapper;
    @Override
    public String findAll(RegisteredFrom registeredFrom) {
        PageHelper.startPage(registeredFrom.getPage(),5);
        List<Map<String, Object>> all = registeredMapper.findAll(registeredFrom);
        PageInfo pageInfo = new PageInfo(all);
        return  JSON.toJSONString(ResponseResult.getSuccessResult(pageInfo,"C200",null));
    }

    @Override
    public String updatePrice(RegisteredUpdateFrom registeredUpdateFrom) {
        //封装
        Registered registered = BeanUtil.toBean(registeredUpdateFrom, Registered.class);
        Integer integer = registeredMapper.updateVersionInteger(registered);
        if(integer!=0){
            return JSON.toJSONString(ResponseResult.getSuccessResult(null,"C200",null), SerializerFeature.DisableCircularReferenceDetect);
        }else {
            return JSON.toJSONString(ResponseResult.getErrorResult("C500"), SerializerFeature.DisableCircularReferenceDetect);
        }
    }
}




