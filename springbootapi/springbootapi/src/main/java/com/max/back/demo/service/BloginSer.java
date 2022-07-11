package com.max.back.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.max.back.demo.entity.Blogin;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author max
 * @since 2021-07-29
 */
public interface BloginSer extends IService<Blogin> {
    public Map<String, String> login(String userName, String password);
}
