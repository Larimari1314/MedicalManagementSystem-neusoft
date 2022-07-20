package com.max.back.neusoft.service;

import com.max.back.neusoft.form.DispensingMedicineFrom;
import com.max.back.neusoft.pojo.Patientmedicine;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
* @author 86155
* @description 针对表【n_patientMedicine】的数据库操作Service
* @createDate 2022-07-17 07:10:04
*/
public interface PatientmedicineService extends IService<Patientmedicine> {
    public String selectByUsernameAndDoctorName(DispensingMedicineFrom dispensingMedicineFrom);
    public String findOrderDrug(String id);
    public Integer getTotalPrice(String id);
    public String payList(String id);
    public String orderDetails(String id);
    public String getDayMedicine();
    public String getAllValues();
    public String getStateStatistics();
}
