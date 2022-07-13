package com.max.back.neusoft.dao;

import com.max.back.neusoft.pojo.Drugspecification;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
* @author 86155
* @description 针对表【n_drugSpecification】的数据库操作Mapper
* @createDate 2022-07-12 11:44:14
* @Entity com.max.back.neusoft.pojo.Drugspecification
*/@Mapper
@Repository
public interface DrugspecificationMapper extends BaseMapper<Drugspecification> {

}




