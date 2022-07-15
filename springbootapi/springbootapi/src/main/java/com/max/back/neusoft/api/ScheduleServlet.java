package com.max.back.neusoft.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.max.back.neusoft.form.ScheduleFrom;
import com.max.back.neusoft.pojo.Schedule;
import com.max.back.neusoft.service.ScheduleService;
import javafx.scene.input.DataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/schedule")
public class ScheduleServlet {
    @Autowired
    private ScheduleService scheduleService;

    @GetMapping("/findScheduleList")
    public String findScheduleList() {
        QueryWrapper<Schedule> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("s_id", "s_name");
        return JSON.toJSONString(scheduleService.listMaps(queryWrapper), SerializerFeature.DisableCircularReferenceDetect);
    }

    @PostMapping("/getAllScheduleList")
    public String getAllScheduleList() {
        List<ScheduleFrom> collect = scheduleService.list().stream().map(
                s -> {
                    SimpleDateFormat format = new SimpleDateFormat("HH:mm");
                    ScheduleFrom scheduleFrom = new ScheduleFrom();
                    scheduleFrom.setId(s.getId());
                    scheduleFrom.setName(s.getName());
                    scheduleFrom.setEnd(format.format(s.getEnd()));
                    scheduleFrom.setStart(format.format(s.getStart()));
                    return scheduleFrom;
                }
        ).collect(Collectors.toList());
        System.out.println(collect);
        return JSON.toJSONString(collect);
    }

}
