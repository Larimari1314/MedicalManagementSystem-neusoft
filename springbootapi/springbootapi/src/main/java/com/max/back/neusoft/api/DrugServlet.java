package com.max.back.neusoft.api;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.max.back.common.http.ResponseResult;
import com.max.back.neusoft.form.DeleteFrom;
import com.max.back.neusoft.form.DrugFindFrom;
import com.max.back.neusoft.form.DrugModifyEnable;
import com.max.back.neusoft.form.DrugSubmitFrom;
import com.max.back.neusoft.pojo.Nondrug;
import com.max.back.neusoft.pojo.Orderdrug;
import com.max.back.neusoft.service.DrugspecificationService;
import com.max.back.neusoft.service.NondrugService;
import com.max.back.neusoft.service.OrderdrugService;
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
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 药品
 */
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

    @Autowired
    private OrderdrugService orderdrugService;

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

    /**
     * 修改药品是否启用
     *
     * @param modifyEnable
     * @return
     */
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

    /**
     * 删除药品
     *
     * @param params
     * @return
     */
    @PostMapping("/deleteByIds")
    public String deleteById(@RequestBody @Valid DeleteFrom params) {
        //在订单表根据用户id查找是否存在相同用户
        AtomicBoolean same = new AtomicBoolean(false);
        params.getIds().stream().forEach(s -> {
            QueryWrapper<Orderdrug> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("o_drugId", s);
            if (orderdrugService.getMap(queryWrapper) != null) {
                same.set(true);
            }
        });
        if (same.get()) {
            return JSON.toJSONString(ResponseResult.getErrorResult("C405"));
        }
        boolean delete = nondrugService.removeByIds(params.getIds());
        if (delete) {
            return JSON.toJSONString(ResponseResult.getSuccessResult(null, "C200", null), SerializerFeature.DisableCircularReferenceDetect);
        } else {
            return JSON.toJSONString(ResponseResult.getErrorResult("C500"), SerializerFeature.DisableCircularReferenceDetect);
        }
    }

    /**
     * 上传药品封面
     *
     * @param file
     */
    @PostMapping("/avatar")
    public void avatar(@RequestBody MultipartFile file) {
        multipartFile = file;
    }

    /**
     * 跟新药品信息
     *
     * @param drugSubmitFrom
     * @return
     * @throws IOException
     */
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

    /**
     * 添加药品
     *
     * @param drugSubmitFrom
     * @return
     * @throws IOException
     */
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

    /**
     * 下载药品模板
     *
     * @param response
     * @throws IOException
     */
    @PostMapping("/dataTemplateDownload")
    public void userTemplateDownload(HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = new String((UUID.randomUUID() + ".xlsx").getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
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

    /**
     * 下载药品excel全部数据
     *
     * @param response
     * @throws IOException
     */
    @PostMapping("/exportData")
    public void exportDataUser(HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = new String((UUID.randomUUID() + ".xlsx").getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
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

    /**
     * 上传excel文件暴漏链接
     *
     * @param file
     */
    @PostMapping("/uploadToServer")
    public void uploadToServer(@RequestBody MultipartFile file) {
        multipartFileExcel = file;
        fileName = UUID.randomUUID().toString();
    }

    /**
     * 对上传文件进行解析
     *
     * @return
     * @throws IOException
     */
    @GetMapping("/analyseFile")
    @ResponseBody
    public String analyseFile() throws IOException {
        try {
            String path = System.getProperty("user.dir");
            File newFile = new File(path + "\\src\\main\\webapp\\static\\" + fileName + ".xlsx");
            multipartFileExcel.transferTo(newFile);
            return nondrugService.analyseFile(fileName);
        } catch (NullPointerException e) {
            return JSON.toJSONString(ResponseResult.getErrorResult("C502"));
        }
    }

    /**
     * 获取已逻辑删除药品信息
     *
     * @return
     */
    @GetMapping("/getDeleteDrug")
    public String getDeletes() {
        return nondrugService.getDeletes();
    }

    /**
     * 永久删除
     *
     * @param id
     * @return
     */
    @PostMapping("/deletePermanently")
    public String deletePermanently(@RequestBody @NotBlank String id) {
        id = id.replace("=", "");
        String path = System.getProperty("user.dir");
        File newFile = new File(path + "\\src\\main\\webapp\\Img\\nonDrug\\" + id + "\\" + id + ".jpg");
        newFile.delete();
        return nondrugService.deletePermanently(id);
    }

    /**
     * 恢复数据
     *
     * @param id
     * @return
     */
    @PostMapping("/dataRecovery")
    public String dataRecovery(@RequestBody @NotBlank String id) {
        id = id.replace("=", "");
        return nondrugService.dataRecovery(id);
    }
}
