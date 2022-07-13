package com.max.back.neusoft.dao;

import com.max.back.neusoft.form.RegisteredFrom;
import com.max.back.neusoft.pojo.Registered;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
* @author 86155
* @description 针对表【n_registered】的数据库操作Mapper
* @createDate 2022-07-12 11:52:08
* @Entity com.max.back.neusoft.pojo.Registered
*/@Mapper
@Repository
public interface RegisteredMapper extends BaseMapper<Registered> {
    public List<Map<String,Object>> findAll(RegisteredFrom name);

    public Integer updateVersionInteger(Registered registered);
}




