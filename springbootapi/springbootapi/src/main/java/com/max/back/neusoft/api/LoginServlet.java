package com.max.back.neusoft.api;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.max.back.common.http.ResponseResult;
import com.max.back.neusoft.form.AdminLoginFrom;
import com.max.back.neusoft.pojo.Landingads;
import com.max.back.neusoft.pojo.Login;
import com.max.back.neusoft.service.LandingadsService;
import com.max.back.neusoft.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/login")
public class LoginServlet {
    @Autowired
    private LandingadsService landingadsService;
    @Autowired
    private LoginService loginService;
    @GetMapping("/getAllAds")
    public String getAllAds() {
        QueryWrapper<Landingads> queryWrapper=new QueryWrapper<>();
        queryWrapper.select("imgUrl","ads","id");
        return JSON.toJSONString(landingadsService.list(queryWrapper));
    }

/*    @PostMapping("/addAdmin")
    public void addAdmin(Login login){
        String s = DigestUtils.md5DigestAsHex(login.getPassword().getBytes(StandardCharsets.UTF_8));
        login.setPassword(s);
        loginService.save(login);
    }*/

    @PostMapping("/getAdminImgUrl")
    public String getAdminImgUrl(@RequestBody String adminUserName) {
        adminUserName = adminUserName.replace("=", "");
        QueryWrapper<Login> querywrapper=new QueryWrapper<>();
        querywrapper.eq("l_userName", adminUserName)
                        .select("l_avatar");
        Map<String, Object> list = loginService.getMap(querywrapper);
        if(list!=null){
            if(list.size()>1){
                return JSON.toJSONString(ResponseResult.getSuccessResult(list, "C201",null));
            }else {
                return JSON.toJSONString(ResponseResult.getSuccessResult(list,"C200",null));
            }
        }else {
            return JSON.toJSONString(ResponseResult.getErrorResult("C404"));
        }
    }
    @PostMapping("/adminRequestLogin")
    public String adminRequestLogin(@RequestBody @Valid AdminLoginFrom adminLoginFrom){
        System.out.println(adminLoginFrom);
        QueryWrapper<Login> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("l_userName", adminLoginFrom.getUsername())
                        .eq("l_password",DigestUtils.md5DigestAsHex(adminLoginFrom.getPassword().getBytes(StandardCharsets.UTF_8)));
        Map<String, Object> map = loginService.getMap(queryWrapper);
        if(map!=null){
            return JSON.toJSONString(ResponseResult.getSuccessResult(map,"C200",null));
        }else {
            return JSON.toJSONString(ResponseResult.getErrorResult("C404"));
        }
    }
}
