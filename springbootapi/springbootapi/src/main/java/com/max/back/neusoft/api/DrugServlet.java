package com.max.back.neusoft.api;

import cn.hutool.core.bean.BeanUtil;
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

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@CrossOrigin
@RestController
@RequestMapping("/nondrug")
public class DrugServlet {
    public static MultipartFile multipartFile = null;
    public static MultipartFile multipartFileExcel = null;
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
                .set("d_price", drugSubmitFrom.getPrice())
                .set("d_version", drugSubmitFrom.getVersion() + 1);
        boolean update = nondrugService.update(updatewrapper);
        if (update) {
            return JSON.toJSONString(ResponseResult.getSuccessResult(null, "C200", null), SerializerFeature.DisableCircularReferenceDetect);
        } else {
            return JSON.toJSONString(ResponseResult.getErrorResult("C500"), SerializerFeature.DisableCircularReferenceDetect);
        }
    }

    @PostMapping("/addDrug")
    public String addDrug(@RequestBody @Valid DrugSubmitFrom drugSubmitFrom) throws IOException {
        if (multipartFile == null) {
            return JSON.toJSONString(ResponseResult.getErrorResult("C404"));
        }
        //创建新的id
        drugSubmitFrom.setId(UUID.randomUUID().toString());
        String path = System.getProperty("user.dir");
        File newFile = new File(path + "\\src\\main\\webapp\\Img\\nonDrug\\" + drugSubmitFrom.getId());
        //创建目录
        newFile.mkdirs();
        //创建文件
        File fileLocation = new File(newFile, "\\" + drugSubmitFrom.getId() + ".jpg");
        multipartFile.transferTo(fileLocation);
        multipartFile = null;
        drugSubmitFrom.setCover("http://localhost:8000/hospital/Img/nonDrug/" + drugSubmitFrom.getId() + "/" + drugSubmitFrom.getId() + ".jpg");
        Nondrug nondrug = BeanUtil.toBean(drugSubmitFrom, Nondrug.class);
        nondrug.setSpecification(drugSubmitFrom.getSname());
        boolean update = nondrugService.save(nondrug);
        if (update) {
            return JSON.toJSONString(ResponseResult.getSuccessResult(null, "C200", null), SerializerFeature.DisableCircularReferenceDetect);
        } else {
            return JSON.toJSONString(ResponseResult.getErrorResult("C500"), SerializerFeature.DisableCircularReferenceDetect);
        }
    }

    @PostMapping("/dataTemplateDownload")
    public void userTemplateDownload(HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = new String((UUID.randomUUID().toString() + ".xlsx").getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
        //设置文件名
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.addHeader("Content-Disposition", "attachment;fileName=" + fileName);
        response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
        //获取产生相应的流对象
        String url = System.getProperty("user.dir") + "\\src\\main\\java\\com\\max\\back\\neusoft\\pojo\\Nondrug.java";
        ByteArrayOutputStream os = nondrugService.drugTemplateDownload(url);
        ServletOutputStream outputStream = response.getOutputStream();
        //将数据从原始字节流对象提取出来写入到servlet对应的输出流中
        os.writeTo(outputStream);
        outputStream.flush();
        outputStream.close();
    }
    @PostMapping("/exportData")
    public void exportDataUser(HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = new String((UUID.randomUUID().toString() + ".xlsx").getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
        //设置文件名
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.addHeader("Content-Disposition", "attachment;fileName=" + fileName);
        response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
        //获取产生相应的流对象
        String url = System.getProperty("user.dir") + "\\src\\main\\java\\com\\max\\back\\neusoft\\pojo\\Nondrug.java";
        ByteArrayOutputStream os = nondrugService.drugExportData(url);
        ServletOutputStream outputStream = response.getOutputStream();
        //将数据从原始字节流对象提取出来写入到servlet对应的输出流中
        os.writeTo(outputStream);
        outputStream.flush();
        outputStream.close();
    }
    @PostMapping("/uploadToServer")
    public void uploadToServer(@RequestBody MultipartFile file) {
        multipartFileExcel = file;
        fileName = UUID.randomUUID().toString();
    }

    @GetMapping("/analyseFile")
    @ResponseBody
    public String analyseFile() throws IOException {
        try {
            String path = System.getProperty("user.dir");
            File newFile = new File(path + "\\src\\main\\webapp\\static\\" + fileName + ".xlsx");
            multipartFileExcel.transferTo(newFile);
            return nondrugService.analyseFile(fileName);
        }catch (NullPointerException e){
            return JSON.toJSONString(ResponseResult.getErrorResult("C502"));
        }
    }
    @GetMapping("/getDeleteDrug")
    public String getDeletes(){
       return nondrugService.getDeletes();
    }
    @PostMapping("/deletePermanently")
    public String deletePermanently(@RequestBody @NotBlank String id) {
        id = id.replace("=", "");
        String path = System.getProperty("user.dir");
        File newFile = new File(path + "\\src\\main\\webapp\\Img\\nonDrug\\" + id+"\\"+id+".jpg");
        newFile.delete();
        return nondrugService.deletePermanently(id);
    }

    @PostMapping("/dataRecovery")
    public String dataRecovery(@RequestBody @NotBlank String id) {
        id = id.replace("=", "");
        return nondrugService.dataRecovery(id);
    }
}
