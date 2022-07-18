package com.max.back.neusoft.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.max.back.common.http.ResponseResult;
import com.max.back.neusoft.dao.PatientmedicineMapper;
import com.max.back.neusoft.form.DispensingMedicineFrom;
import com.max.back.neusoft.form.PatientMedicineExhibit;
import com.max.back.neusoft.pojo.Patientmedicine;
import com.max.back.neusoft.service.PatientmedicineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @author 86155
 * @description 针对表【n_patientMedicine】的数据库操作Service实现
 * @createDate 2022-07-17 07:10:04
 */
@Service
public class PatientmedicineServiceImpl extends ServiceImpl<PatientmedicineMapper, Patientmedicine>
        implements PatientmedicineService {
    @Autowired
    private PatientmedicineMapper patientmedicineMapper;

    @Override
    public String selectByUsernameAndDoctorName(DispensingMedicineFrom dispensingMedicineFrom) {
        PageHelper.startPage(dispensingMedicineFrom.getPage(), 50);
        List<PatientMedicineExhibit> mapList = patientmedicineMapper.selectByUsernameAndDoctorName(dispensingMedicineFrom).stream().map(
                s -> {
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                    PatientMedicineExhibit patientMedicineExhibit = BeanUtil.toBean(s, PatientMedicineExhibit.class);
                    patientMedicineExhibit.setTimes(simpleDateFormat.format(s.get("times")));
                    return patientMedicineExhibit;
                }
        ).collect(Collectors.toList());
        System.out.println(mapList.size());
        PageInfo pageInfo = new PageInfo(mapList);
        return JSON.toJSONString(ResponseResult.getSuccessResult(pageInfo, "C200", null), SerializerFeature.DisableCircularReferenceDetect);
    }

    @Override
    public String findOrderDrug(String id) {
        List<Map<String, Object>> orderDrug = patientmedicineMapper.findOrderDrug(id);
        orderDrug.forEach(
                s -> {
                    int i = (Integer) s.get("number") * (Integer) s.get("dprice");
                    s.put("zprice", i);
                }
        );
        return JSON.toJSONString(ResponseResult.getSuccessResult(orderDrug, "C200", null), SerializerFeature.DisableCircularReferenceDetect);
    }

    @Override
    public Integer getTotalPrice(String id) {
        List<Map<String, Object>> totalPrice = patientmedicineMapper.getTotalPrice(id);
        AtomicInteger number = new AtomicInteger();
        totalPrice.stream().forEach(s -> {
            number.addAndGet((Integer) s.get("number") * (Integer) s.get("price"));
        });
        return number.get();
    }

    @Override
    public String payList(String id) {
        List<Map<String, Object>> res = patientmedicineMapper.payList(id);
        return JSON.toJSONString(ResponseResult.getSuccessResult(res, "C200", null));
    }

    @Override
    public String orderDetails(String id) {
        List<Map<String, Object>> res = patientmedicineMapper.orderDetails(id);
        for (Map<String, Object> re : res) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            re.put("pdate", simpleDateFormat.format(re.get("pdate")));
        }
        return JSON.toJSONString(ResponseResult.getSuccessResult(res,"C200",null));
    }
}




