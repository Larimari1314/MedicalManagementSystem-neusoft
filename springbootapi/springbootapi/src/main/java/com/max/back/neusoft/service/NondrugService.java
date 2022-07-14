package com.max.back.neusoft.service;

import com.max.back.neusoft.form.DrugFindFrom;
import com.max.back.neusoft.pojo.Nondrug;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author 86155
* @description 针对表【n_nonDrug】的数据库操作Service
* @createDate 2022-07-12 11:48:31
*/
public interface NondrugService extends IService<Nondrug> {

    String getAllDrugByRequire(DrugFindFrom drugFindFrom);
}
