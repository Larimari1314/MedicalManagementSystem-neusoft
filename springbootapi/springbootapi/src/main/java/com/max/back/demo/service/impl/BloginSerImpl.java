package com.max.back.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.max.back.demo.dao.BloginDao;
import com.max.back.demo.entity.Blogin;
import com.max.back.demo.service.BloginSer;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author max
 * @since 2021-07-29
 */
@Service
public class BloginSerImpl extends ServiceImpl<BloginDao, Blogin> implements BloginSer {
    @Override
    public Map<String, String> login(String userName, String password) {
        Map<String, String> map = new HashMap<>();
        int res = query().eq("username", userName).count();
        if(res == 0) {
            // 用户名不存在
            map.put("res", "-1");
            return map;
        }
        Blogin blogin = query().eq("username", userName)
                .one();
        if(password.equals(blogin.getPassword())) {
            // 登录成功
            map.put("res", "1");
            map.put("username", blogin.getUsername());
            map.put("avatar", blogin.getAvatar());
            return map;
        } else {
            // 登录不成功
            map.put("res", "-2");
            return map;
        }
    }
}
