package com.max.back.neusoft.dao;

import com.max.back.neusoft.form.DispensingMedicineFrom;
import com.max.back.neusoft.pojo.Patientmedicine;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
* @author 86155
* @description 针对表【n_patientMedicine】的数据库操作Mapper
* @createDate 2022-07-17 07:10:04
* @Entity com.max.back.neusoft.pojo.Patientmedicine
*/
@Mapper
@Repository
public interface PatientmedicineMapper extends BaseMapper<Patientmedicine> {
    public List<Map<String,Object>> selectByUsernameAndDoctorName(DispensingMedicineFrom dispensingMedicineFrom);

    public List<Map<String,Object>> findOrderDrug(String id);
    //获取价格和信息
    public List<Map<String,Object>> getTotalPrice(String id);
    public List<Map<String,Object>> payList(String id);
}




