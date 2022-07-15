package com.max.back.neusoft.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.max.back.common.http.ResponseResult;
import com.max.back.neusoft.form.AdminLoginFrom;
import com.max.back.neusoft.form.LoginInformationFrom;
import com.max.back.neusoft.pojo.Landingads;
import com.max.back.neusoft.pojo.Login;
import com.max.back.neusoft.service.LandingadsService;
import com.max.back.neusoft.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpRequest;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@CrossOrigin
@RestController
@RequestMapping("/login")
public class LoginServlet {

    @Autowired
    private RedisTemplate redisTemplate;
    private static MultipartFile multipartFile;
    @Autowired
    private LandingadsService landingadsService;
    @Autowired
    private LoginService loginService;

    @GetMapping("/getAllAds")
    public String getAllAds() {
        QueryWrapper<Landingads> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("imgUrl", "ads", "id");
        return JSON.toJSONString(landingadsService.list(queryWrapper));
    }

    @GetMapping("/logout")
    public String logout(HttpServletResponse response) {
        Cookie cookie = new Cookie("_neusoft_admin", "");
        cookie.setPath("/");
        response.addCookie(cookie);
        return JSON.toJSONString(ResponseResult.getSuccessResult(null,"C200",null));
    }
/*    @PostMapping("/addAdmin")
    public void addAdmin(Login login){
        String s = DigestUtils.md5DigestAsHex(login.getPassword().getBytes(StandardCharsets.UTF_8));
        login.setPassword(s);
        loginService.save(login);
    }*/

    @PostMapping("/getAdminImgUrl")
    public String getAdminImgUrl(@RequestBody @NotBlank String adminUserName) {
        adminUserName = adminUserName.replace("=", "");
        QueryWrapper<Login> querywrapper = new QueryWrapper<>();
        querywrapper.eq("l_userName", adminUserName)
                .select("l_avatar");
        Map<String, Object> list = loginService.getMap(querywrapper);
        if (list != null) {
            if (list.size() > 1) {
                return JSON.toJSONString(ResponseResult.getSuccessResult(list, "C201", null));
            } else {
                return JSON.toJSONString(ResponseResult.getSuccessResult(list, "C200", null));
            }
        } else {
            return JSON.toJSONString(ResponseResult.getErrorResult("C404"));
        }
    }

    @PostMapping("/adminRequestLogin")
    public String adminRequestLogin(@RequestBody @Valid AdminLoginFrom adminLoginFrom, HttpServletResponse response) {
        QueryWrapper<Login> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("l_userName", adminLoginFrom.getUsername())
                .eq("l_password", DigestUtils.md5DigestAsHex(adminLoginFrom.getPassword().getBytes(StandardCharsets.UTF_8)));
        Map<String, Object> map = loginService.getMap(queryWrapper);
        if (map != null) {
            String cookieValue = UUID.randomUUID().toString();
            Cookie cookie=new Cookie("_neusoft_admin",cookieValue );
            cookie.setPath("/");
            response.addCookie(cookie);
            redisTemplate.boundValueOps(cookieValue).set(map.get("permission"), 60, TimeUnit.MINUTES);
            return JSON.toJSONString(ResponseResult.getSuccessResult(map, "C200", null));
        } else {
            return JSON.toJSONString(ResponseResult.getErrorResult("C404"));
        }
    }

    @PostMapping("/avatar")
    public void avatar(@RequestBody MultipartFile file) {
        multipartFile = file;
    }

    @PostMapping("/modifyLoginInformation")
    public String modifyLoginInformation(@RequestBody @Valid LoginInformationFrom loginInformationFrom) throws IOException {
        if (multipartFile != null) {
            System.out.println(111);
            String path = System.getProperty("user.dir");
            File newFile = new File(path + "\\src\\main\\webapp\\Img\\admin\\" + loginInformationFrom.getId()+"\\"+loginInformationFrom.getId() + ".jpg");
            multipartFile.transferTo(newFile);
        }
        QueryWrapper<Login> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("l_userName",loginInformationFrom.getUsername());
        Map<String, Object> map = loginService.getMap(queryWrapper);
        if(map!=null){
            //存在用户名
            return JSON.toJSONString(ResponseResult.getErrorResult("C405"));
        }
        UpdateWrapper<Login> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("l_id", loginInformationFrom.getId())
                .set("l_userName", loginInformationFrom.getUsername())
                .set("l_password",  DigestUtils.md5DigestAsHex(loginInformationFrom.getPassword().getBytes(StandardCharsets.UTF_8)));
        boolean update = loginService.update(updateWrapper);
        if (update) {
            return JSON.toJSONString(ResponseResult.getSuccessResult(null, "C200", null), SerializerFeature.DisableCircularReferenceDetect);
        } else {
            return JSON.toJSONString(ResponseResult.getErrorResult("C500"), SerializerFeature.DisableCircularReferenceDetect);
        }
    }
}