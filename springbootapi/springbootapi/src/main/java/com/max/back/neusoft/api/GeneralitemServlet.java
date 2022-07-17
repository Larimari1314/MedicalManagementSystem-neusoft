package com.max.back.neusoft.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.max.back.common.http.ResponseResult;
import com.max.back.neusoft.form.GeneralitemFrom;
import com.max.back.neusoft.form.NameFrom;
import com.max.back.neusoft.pojo.Generalitem;
import com.max.back.neusoft.service.GeneralitemService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/generalitem")
public class GeneralitemServlet {
    @Autowired
    private GeneralitemService generalitemService;

    //查找性别
    @GetMapping("/findGender")
    public String findGender() {
        return generalitemService.findGender();
    }

    @GetMapping("/findByRegistered")
    public String findByRegistered() {
        return generalitemService.findByRegistered();
    }

    @PostMapping("/getAll")
    public String getAllGeneralitem(@RequestBody NameFrom name) {
        QueryWrapper<Generalitem> findByName = new QueryWrapper<>();
        findByName.like(StringUtils.isNotBlank(name.getName()), "value", name.getName());
        return JSON.toJSONString(generalitemService.list(findByName));
    }

    @PostMapping("/editGeneralitem")
    public String editGeneralitem(@RequestBody @Valid GeneralitemFrom generalitemFrom) {
        UpdateWrapper<Generalitem> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("coding", generalitemFrom.getCoding())
                .set("value", generalitemFrom.getValue());
        boolean update = generalitemService.update(updateWrapper);
        if (update) {
            return JSON.toJSONString(ResponseResult.getSuccessResult(null, "C200", null), SerializerFeature.DisableCircularReferenceDetect);
        } else {
            return JSON.toJSONString(ResponseResult.getErrorResult("C500"), SerializerFeature.DisableCircularReferenceDetect);
        }
    }
    @GetMapping("/getMedicationStatus")
    public String getMedicationStatus(){
       return generalitemService.getMedicationStatus();
    }
}
