package com.max.back.neusoft.service;

import com.max.back.neusoft.pojo.Generalitem;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
* @author 86155
* @description 针对表【n_generalItem】的数据库操作Service
* @createDate 2022-07-12 11:45:29
*/
public interface GeneralitemService extends IService<Generalitem> {
    public String findGender();
    public String findByRegistered();
}
