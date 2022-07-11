package com.max.back.demo.api;


import com.max.back.common.config.Swagger2Configuration;
import com.max.back.common.http.ResponseResult;
import com.max.back.common.sercurity.anno.AuthIgrone;
import com.max.back.common.sercurity.utils.ConfigrarionBean;
import com.max.back.common.sercurity.utils.JwtTokenUtil;
import com.max.back.common.sercurity.utils.RedisUtil;
import com.max.back.demo.form.LoginForm;
import com.max.back.demo.service.BloginSer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author max
 * @since 2021-07-29
 */
@Api(tags = {Swagger2Configuration.TAG_API})
@CrossOrigin
@RestController
@RequestMapping("/api/authentication")
public class BloginCtl {
    @Autowired
    private BloginSer ser;

    @Autowired
    private JwtTokenUtil jwtUtil;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private ConfigrarionBean config;

    @ApiOperation(value = "登录")
//    @ApiImplicitParams(
//            {@ApiImplicitParam(name = "form", value = "用户信息", required = true, paramType = "body", dataType = "LoginForm")})
    @AuthIgrone
    @PostMapping("/login")
    public ResponseResult<Map<String, String>> login(@RequestBody @Valid LoginForm form) {
        Map<String, String> map = ser.login(form.getUserName(), form.getPassword());
        if("1".equals(map.get("res"))) {
            String token = jwtUtil.createToken(map.get("username"));
            redisUtil.set("login_" + map.get("username"), map.get("username"),config.getRedisExpiration());
            Map<String, String> res = new HashMap<>();
            res.put("authtoken", token);
            res.put("username", map.get("username"));
            res.put("avatar", map.get("avatar"));
            return ResponseResult.getSuccessResult(res);
        } else if("-1".equals(map.get("res"))) {
            return ResponseResult.getMessageResult(null, "A001");
        } else {
            return ResponseResult.getMessageResult(null, "A002");
        }
    }
}