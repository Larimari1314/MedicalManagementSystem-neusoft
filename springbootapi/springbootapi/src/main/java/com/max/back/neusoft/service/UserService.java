package com.max.back.neusoft.service;

import com.max.back.neusoft.form.UserFindFrom;
import com.max.back.neusoft.pojo.User;
import com.baomidou.mybatisplus.extension.service.IService;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Map;

/**
* @author 86155
* @description 针对表【n_user】的数据库操作Service
* @createDate 2022-07-12 11:55:22
*/
public interface UserService extends IService<User> {
    public String findUser(UserFindFrom userFindFrom);

    ByteArrayOutputStream doctorTemplateDownload(String url);

    ByteArrayOutputStream userExportData(String url);

    String analyseFile(String fileName);

    public String  deletePermanently(String id);
    public String dataRecovery(String id);
    String getDeletes();
}
