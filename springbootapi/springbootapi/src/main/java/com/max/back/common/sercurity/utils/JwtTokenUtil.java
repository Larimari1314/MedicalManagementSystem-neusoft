package com.max.back.common.sercurity.utils;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.jwt.JWTPayload;
import cn.hutool.jwt.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
//基于JWT(JSON Web Tokens) 的 Token 认证法
//cn.hutool小而全的Java工具类
public class JwtTokenUtil {
	@Autowired
	private ConfigrarionBean myConfig;
    @Autowired
    private RedisUtil redisUtil;

    public String createToken(String userName) {
        DateTime now = DateTime.now();
        //时间偏移量，当前时间偏移10分钟
        DateTime newTime = now.offsetNew(DateField.MINUTE, myConfig.getExpiration());
        System.out.println("newTimenewTimenewTime:" + newTime);
        Map<String,Object> payload = new HashMap<>();
        // 签发人
        payload.put(JWTPayload.ISSUER, myConfig.getIss());
        //签发时间
        payload.put(JWTPayload.ISSUED_AT, now);
        //过期时间
        payload.put(JWTPayload.EXPIRES_AT, newTime);
        //生效时间
        payload.put(JWTPayload.NOT_BEFORE, now);
        //载荷
        payload.put(HttpJwtTokenUtil.KEY_USERNAME, userName);
        String token = JWTUtil.createToken(payload, myConfig.getSecret().getBytes());
        return token;
    }

    /**
     * 验证token签名
     * @return
     */
    public boolean verify(String token) {
        return JWTUtil.verify(token, myConfig.getSecret().getBytes());
    }

    /**
     * 从token获取用户信息
     * @param token
     * @return
     */
    public String getUsername(String token) {
        return JWTUtil.parseToken(token).getPayload(HttpJwtTokenUtil.KEY_USERNAME).toString();
    }

    /**
     * 是否已过期, 未过期，延长时间
     * 
     * @param token
     * @return
     */
    public boolean isExpiration(String token) {
        String userName = getUsername(token);
        String key = "login_" + userName;
        boolean hasEx = redisUtil.hasKey(key);
        if(hasEx) {
            //验证缓存redis数据是否过期
            redisUtil.expire(key, myConfig.getRedisExpiration());
            return true;
        } else {
            return false;
        }
    }
}
