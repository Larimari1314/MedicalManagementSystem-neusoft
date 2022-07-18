package com.max.back.neusoft.service;

import com.max.back.neusoft.pojo.Orderdrug;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author 86155
* @description 针对表【n_orderDrug】的数据库操作Service
* @createDate 2022-07-17 07:10:40
*/
public interface OrderdrugService extends IService<Orderdrug> {
    public boolean createSameOrder(String oldId,String newId);
}
