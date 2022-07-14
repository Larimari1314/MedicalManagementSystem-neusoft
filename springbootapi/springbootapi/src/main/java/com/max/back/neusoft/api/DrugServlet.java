package com.max.back.neusoft.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.max.back.common.http.ResponseResult;
import com.max.back.neusoft.form.DeleteFrom;
import com.max.back.neusoft.form.DrugFindFrom;
import com.max.back.neusoft.form.DrugModifyEnable;
import com.max.back.neusoft.form.DrugSubmitFrom;
import com.max.back.neusoft.pojo.Nondrug;
import com.max.back.neusoft.service.DrugspecificationService;
import com.max.back.neusoft.service.NondrugService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;

@CrossOrigin
@RestController
@RequestMapping("/nondrug")
public class DrugServlet {
    public static MultipartFile multipartFile = null;
    public static String fileName = null;
    @Autowired
    private DrugspecificationService drugspecificationService;
    @Autowired
    private NondrugService nondrugService;

    //获取药品规格
    @GetMapping("/findAllSpecification")
    public String findAllSpecification() {
        return JSON.toJSONString(drugspecificationService.list(), SerializerFeature.DisableCircularReferenceDetect);
    }

    //根据要求获取药品
    @PostMapping("/getAllDrugByRequire")
    public String getAllDrugByRequire(@RequestBody @Valid DrugFindFrom drugFindFrom) {
        return nondrugService.getAllDrugByRequire(drugFindFrom);
    }

    @PostMapping("/modifyEnable")
    public String modifyEnable(@RequestBody @Valid DrugModifyEnable modifyEnable) {
        UpdateWrapper<Nondrug> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("d_id", modifyEnable.getId())
                .set("d_enable", modifyEnable.getEnable());
        boolean update = nondrugService.update(updateWrapper);
        if (update) {
            return JSON.toJSONString(ResponseResult.getSuccessResult(null, "C200", null));
        } else {
            return JSON.toJSONString(ResponseResult.getErrorResult("C500"));
        }
    }

    @PostMapping("/deleteByIds")
    public String deleteById(@RequestBody @Valid DeleteFrom params) {
        boolean delete = nondrugService.removeByIds(params.getIds());
        if (delete) {
            return JSON.toJSONString(ResponseResult.getSuccessResult(null, "C200", null), SerializerFeature.DisableCircularReferenceDetect);
        } else {
            return JSON.toJSONString(ResponseResult.getErrorResult("C500"), SerializerFeature.DisableCircularReferenceDetect);
        }
    }

    @PostMapping("/avatar")
    public void avatar(@RequestBody MultipartFile file) {
        multipartFile = file;
    }

    @PostMapping("/updateDrug")
    public String updateUser(@RequestBody DrugSubmitFrom drugSubmitFrom) throws IOException {
        System.out.println(drugSubmitFrom);
        if (multipartFile != null) {
            String path = System.getProperty("user.dir");
            File newFile = new File(path + "\\src\\main\\webapp\\Img\\nonDrug\\" + drugSubmitFrom.getId());
            //创建目录
            newFile.mkdirs();
            //创建文件
            File fileLocation = new File(newFile, "\\" + drugSubmitFrom.getId() + ".jpg");
            multipartFile.transferTo(fileLocation);
            multipartFile = null;
            drugSubmitFrom.setCover("http://localhost:8000/hospital/Img/nonDrug/" + drugSubmitFrom.getId() + "/" + drugSubmitFrom.getId() + ".jpg");
        }
        UpdateWrapper<Nondrug> updatewrapper = new UpdateWrapper<>();
        updatewrapper.eq("d_id", drugSubmitFrom.getId())
                .eq("d_version", drugSubmitFrom.getVersion())
                .set("d_name", drugSubmitFrom.getName())
                .set("d_number", drugSubmitFrom.getNumber())
                .set("d_cover", drugSubmitFrom.getCover())
                .set("d_price", drugSubmitFrom.getPrice());
        boolean update = nondrugService.update(updatewrapper);
        if (update) {
            return JSON.toJSONString(ResponseResult.getSuccessResult(null, "C200", null), SerializerFeature.DisableCircularReferenceDetect);
        } else {
            return JSON.toJSONString(ResponseResult.getErrorResult("C500"), SerializerFeature.DisableCircularReferenceDetect);
        }
    }
}
