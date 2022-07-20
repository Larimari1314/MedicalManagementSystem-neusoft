package com.max.back.neusoft.service;

import com.max.back.neusoft.form.DoctorFindFrom;
import com.max.back.neusoft.form.DoctorRegisteredFrom;
import com.max.back.neusoft.pojo.Doctor;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
* @author 86155
* @description 针对表【n_doctor】的数据库操作Service
* @createDate 2022-07-12 11:41:46
*/
public interface DoctorService extends IService<Doctor> {
    public String findByRegis(DoctorRegisteredFrom doctorRegisteredFrom);
    public String findByRegistered(DoctorFindFrom doctorFindFrom);

    public String checkDeleteDoctor();
    public String  deletePermanently(String id);
    public String dataRecovery(String id);

    public String getRegisDoctor();

}
