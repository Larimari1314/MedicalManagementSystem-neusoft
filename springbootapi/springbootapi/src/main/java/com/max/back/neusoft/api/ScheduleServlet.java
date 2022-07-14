package com.max.back.neusoft.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.max.back.neusoft.pojo.Schedule;
import com.max.back.neusoft.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/schedule")
public class ScheduleServlet {
    @Autowired
    private ScheduleService scheduleService;
    @GetMapping("/findScheduleList")
    public String findScheduleList() {
        QueryWrapper<Schedule> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("s_id","s_name");
        return JSON.toJSONString( scheduleService.listMaps(queryWrapper), SerializerFeature.DisableCircularReferenceDetect);
    }
    @PostMapping("/getAllScheduleList")
    public String getAllScheduleList(){
        return JSON.toJSONString(scheduleService.list());
    }
}
