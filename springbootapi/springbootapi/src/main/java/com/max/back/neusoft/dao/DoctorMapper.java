package com.max.back.neusoft.dao;

import com.max.back.neusoft.form.DoctorFindFrom;
import com.max.back.neusoft.form.DoctorRegisteredFrom;
import com.max.back.neusoft.pojo.Doctor;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface DoctorMapper extends BaseMapper<Doctor> {
    public List<Map<String,Object>> findByRegis(DoctorRegisteredFrom doctorRegisteredFrom);
    List<Map<String,Object>> findDoctor(DoctorFindFrom doctorFindFrom);
    public List<Map<String,Object>> checkDeleteDoctor();

    public Integer deletePermanently(String id);

    public Integer dataRecovery(String id);

    public List<Map<String,Object>> getRegisDoctor();

}




