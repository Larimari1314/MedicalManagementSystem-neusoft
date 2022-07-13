package com.max.back.neusoft.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.max.back.neusoft.form.DrugFindFrom;
import com.max.back.neusoft.service.DrugspecificationService;
import com.max.back.neusoft.service.NondrugService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/nondrug")
public class DrugServlet {
    @Autowired
    private DrugspecificationService drugspecificationService;
    @Autowired
    private NondrugService nondrugService;
    //获取药品规格
    @GetMapping("/findAllSpecification")
    public String findAllSpecification(){
        return JSON.toJSONString(drugspecificationService.list(), SerializerFeature.DisableCircularReferenceDetect);
    }
    //根据要求获取药品
    @PostMapping("/getAllDrugByRequire")
    public String getAllDrugByRequire(@RequestBody @Valid DrugFindFrom drugFindFrom){

        return "";
    }
}
