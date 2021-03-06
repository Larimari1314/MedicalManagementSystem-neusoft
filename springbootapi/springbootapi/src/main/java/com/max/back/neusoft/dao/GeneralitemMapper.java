package com.max.back.neusoft.dao;

import com.max.back.neusoft.form.DoctorFindFrom;
import com.max.back.neusoft.pojo.Generalitem;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
* @author 86155
* @description 针对表【n_generalItem】的数据库操作Mapper
* @createDate 2022-07-12 11:45:29
* @Entity com.max.back.neusoft.pojo.Generalitem
*/@Mapper
@Repository
public interface GeneralitemMapper extends BaseMapper<Generalitem> {
    public List<Map<String,Object>> findGender();

    public List<Map<String, Object>> findByRegistered();

    public List<Map<String,Object>> getMedicationStatus();
}




