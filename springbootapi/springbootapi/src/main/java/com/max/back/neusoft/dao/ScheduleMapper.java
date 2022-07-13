package com.max.back.neusoft.dao;

import com.max.back.neusoft.pojo.Schedule;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
* @author 86155
* @description 针对表【n_schedule】的数据库操作Mapper
* @createDate 2022-07-12 11:53:45
* @Entity com.max.back.neusoft.pojo.Schedule
*/@Mapper
@Repository
public interface ScheduleMapper extends BaseMapper<Schedule> {

}




