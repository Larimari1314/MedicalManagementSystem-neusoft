package com.max.back.neusoft.dao;

import com.max.back.neusoft.form.DoctorFindFrom;
import com.max.back.neusoft.form.DoctorRegisteredFrom;
import com.max.back.neusoft.pojo.Doctor;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
* @author 86155
* @description 针对表【n_doctor】的数据库操作Mapper
* @createDate 2022-07-12 11:41:46
* @Entity com.max.back.neusoft.pojo.Doctor
*/@Mapper
@Repository
public interface DoctorMapper extends BaseMapper<Doctor> {
    public List<Map<String,Object>> findByRegis(DoctorRegisteredFrom doctorRegisteredFrom);
    List<Map<String,Object>> findDoctor(DoctorFindFrom doctorFindFrom);
}




