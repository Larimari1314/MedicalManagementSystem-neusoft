package com.max.back.neusoft.api;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.max.back.common.http.ResponseResult;
import com.max.back.neusoft.form.UserDeleteFrom;
import com.max.back.neusoft.form.UserFindFrom;
import com.max.back.neusoft.form.UserUpdateFrom;
import com.max.back.neusoft.pojo.Department;
import com.max.back.neusoft.pojo.User;
import com.max.back.neusoft.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.UUID;

@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserServlet {
    public static MultipartFile multipartFile = null;
    public static String fileName=null;
    @Autowired
    private UserService userService;

    //获取全部用户信息
    @PostMapping("/findUser")
    public String findAllUser(@RequestBody @Valid UserFindFrom userFindFrom) {
        return userService.findUser(userFindFrom);
    }

    @PostMapping("/deleteByIds")
    public String deleteById(@RequestBody @Valid UserDeleteFrom params) {
        boolean delete = userService.removeByIds(params.getIds());
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

    @PostMapping("/updateUser")
    public String updateUser(@RequestBody UserUpdateFrom userUpdateFrom) throws IOException {
        System.out.println(userUpdateFrom);
        if (multipartFile != null) {
            System.out.println("dedededed");
            String path = System.getProperty("user.dir");
            File newFile = new File(path + "\\src\\main\\webapp\\Img\\user\\" + userUpdateFrom.getId() + ".jpg");
            multipartFile.transferTo(newFile);
            multipartFile = null;
            userUpdateFrom.setAvatar("http://localhost:8000/hospital/Img/user/" + userUpdateFrom.getId() + ".jpg");
        }
        int age = new Date().getYear() - userUpdateFrom.getBirthday().getYear();
        userUpdateFrom.setAge(age);
        UpdateWrapper<User> updatewrapper = new UpdateWrapper<>();
        updatewrapper.eq("u_id", userUpdateFrom.getId())
                .set("u_username", userUpdateFrom.getUsername())
                .set("u_age", userUpdateFrom.getAge())
                .set("u_avatar", userUpdateFrom.getAvatar())
                .set("u_birthday", userUpdateFrom.getBirthday());
        boolean update = userService.update(updatewrapper);
        if (update) {
            return JSON.toJSONString(ResponseResult.getSuccessResult(null, "C200", null), SerializerFeature.DisableCircularReferenceDetect);
        } else {
            return JSON.toJSONString(ResponseResult.getErrorResult("C500"), SerializerFeature.DisableCircularReferenceDetect);
        }
    }

    @PostMapping("/addUser")
    public String addUser(@RequestBody UserUpdateFrom userUpdateFrom) throws IOException {
        String id = UUID.randomUUID().toString();
        userUpdateFrom.setId(id);
        //首次添加用户u西安则默认头像
        userUpdateFrom.setAvatar("http://localhost:8000/hospital/Img/user/default.jpg");
        int age = new Date().getYear() - userUpdateFrom.getBirthday().getYear();
        userUpdateFrom.setAge(age);
        User department = BeanUtil.toBean(userUpdateFrom, User.class);
        boolean add = userService.save(department);
        if (add) {
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
        String url = System.getProperty("user.dir") + "\\src\\main\\java\\com\\max\\back\\neusoft\\pojo\\User.java";
        ByteArrayOutputStream os = userService.doctorTemplateDownload(url);
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
        String url = System.getProperty("user.dir") + "\\src\\main\\java\\com\\max\\back\\neusoft\\pojo\\User.java";
        ByteArrayOutputStream os = userService.userExportData(url);
        ServletOutputStream outputStream = response.getOutputStream();
        //将数据从原始字节流对象提取出来写入到servlet对应的输出流中
        os.writeTo(outputStream);
        outputStream.flush();
        outputStream.close();
    }
    @PostMapping("/uploadToServer")
    public void uploadToServer(@RequestBody MultipartFile file) {
        multipartFile = file;
        fileName = UUID.randomUUID().toString();
    }
    @GetMapping("/analyseFile")
    @ResponseBody
    public String analyseFile() throws IOException {
        String path = System.getProperty("user.dir");
        File newFile = new File(path + "\\src\\main\\webapp\\static\\" + fileName + ".xlsx");
        multipartFile.transferTo(newFile);
        return  userService.analyseFile(fileName);
    }
}
