package com.max.back.neusoft.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.max.back.common.http.ResponseResult;
import com.max.back.neusoft.dao.NondrugMapper;
import com.max.back.neusoft.form.DrugFindFrom;
import com.max.back.neusoft.pojo.Nondrug;
import com.max.back.neusoft.service.NondrugService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author 86155
 * @description 针对表【n_nonDrug】的数据库操作Service实现
 * @createDate 2022-07-12 11:48:31
 */
@Service
public class NondrugServiceImpl extends ServiceImpl<NondrugMapper, Nondrug>
        implements NondrugService {
    @Autowired
    private NondrugMapper nondrugMapper;

    @Override
    public String getAllDrugByRequire(DrugFindFrom drugFindFrom) {
        PageHelper.startPage(drugFindFrom.getPage(),10);
        List<Map<String, Object>> allDrugByRequire = nondrugMapper.getAllDrugByRequire(drugFindFrom);
        PageInfo pageInfo=new PageInfo(allDrugByRequire);
        return JSON.toJSONString(ResponseResult.getSuccessResult(pageInfo,"C200", null), SerializerFeature.DisableCircularReferenceDetect);
    }
}




