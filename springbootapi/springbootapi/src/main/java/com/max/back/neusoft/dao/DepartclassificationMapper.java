package com.max.back.neusoft.dao;

import com.max.back.neusoft.pojo.Departclassification;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
* @author 86155
* @description 针对表【n_departClassification】的数据库操作Mapper
* @createDate 2022-07-12 11:36:07
* @Entity com.max.back.neusoft.pojo.Departclassification
*/
@Mapper
@Repository
public interface DepartclassificationMapper extends BaseMapper<Departclassification> {
    
}




