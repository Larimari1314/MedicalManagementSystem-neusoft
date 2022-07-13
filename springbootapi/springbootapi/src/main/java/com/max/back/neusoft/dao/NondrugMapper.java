package com.max.back.neusoft.dao;

import com.max.back.neusoft.pojo.Nondrug;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
* @author 86155
* @description 针对表【n_nonDrug】的数据库操作Mapper
* @createDate 2022-07-12 11:48:31
* @Entity com.max.back.neusoft.pojo.Nondrug
*/@Mapper
@Repository
public interface NondrugMapper extends BaseMapper<Nondrug> {

}




