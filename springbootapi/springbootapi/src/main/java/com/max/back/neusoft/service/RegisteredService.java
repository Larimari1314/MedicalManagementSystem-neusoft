package com.max.back.neusoft.service;

import com.max.back.neusoft.form.RegisteredFrom;
import com.max.back.neusoft.form.RegisteredUpdateFrom;
import com.max.back.neusoft.pojo.Registered;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
* @author 86155
* @description 针对表【n_registered】的数据库操作Service
* @createDate 2022-07-12 11:52:08
*/
public interface RegisteredService extends IService<Registered> {
    public String findAll(RegisteredFrom registeredFrom);

    String updatePrice(RegisteredUpdateFrom registeredUpdateFrom);
}
