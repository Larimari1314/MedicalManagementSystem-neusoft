package com.max.back.neusoft.api;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.max.back.common.http.ResponseResult;
import com.max.back.neusoft.service.DepartclassificationService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 科室级别
 */
@CrossOrigin
@RestController
@RequestMapping("/departClassification")
public class DepartclassificationServlet {
    @Autowired
    private DepartclassificationService departclassificationService;

    /**
     * 获取科室类别列表
     * @return
     */
    @GetMapping("/getDepartClassList")
    @ApiOperation(value = "获取科室类别列表")
    public String getDepartClassList(){
       return JSON.toJSONString(ResponseResult.getSuccessResult(departclassificationService.list()), SerializerFeature.DisableCircularReferenceDetect);
    }
}




