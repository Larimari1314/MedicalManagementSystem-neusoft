package com.max.back.neusoft.dao;

import com.max.back.neusoft.pojo.Orderdrug;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
* @author 86155
* @description 针对表【n_orderDrug】的数据库操作Mapper
* @createDate 2022-07-17 07:10:40
* @Entity com.max.back.neusoft.pojo.Orderdrug
*/
@Mapper
@Repository
public interface OrderdrugMapper extends BaseMapper<Orderdrug> {

}




