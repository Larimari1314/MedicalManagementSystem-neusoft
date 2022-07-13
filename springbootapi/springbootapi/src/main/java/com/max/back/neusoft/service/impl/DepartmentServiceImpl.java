package com.max.back.neusoft.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.max.back.common.http.ResponseResult;
import com.max.back.common.http.ResultType;
import com.max.back.neusoft.form.DepartmentFindFrom;
import com.max.back.neusoft.pojo.Department;
import com.max.back.neusoft.service.DepartmentService;
import com.max.back.neusoft.dao.DepartmentMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
* @author 86155
* @description 针对表【n_department】的数据库操作Service实现
* @createDate 2022-07-12 10:57:32
*/
@Service
public class DepartmentServiceImpl extends ServiceImpl<DepartmentMapper, Department>
    implements DepartmentService{
    @Autowired
    private DepartmentMapper departmentMapper;
    public String findDepartment(DepartmentFindFrom departmentFindFrom){
        PageHelper.startPage(departmentFindFrom.getPage(), 5);
        /*QueryWrapper<Department> queryWrapper = new QueryWrapper();
        queryWrapper.like(StringUtils.isNotBlank(departmentFindFrom.getName()), "d_name", departmentFindFrom.getName())
                .eq(StringUtils.isNotBlank(departmentFindFrom.getDepartmentClassification()),"d_classification", departmentFindFrom.getDepartmentClassification());
        List<Department> list = list(queryWrapper);*/
        List<Map<String, Object>> list = departmentMapper.findDepartment(departmentFindFrom);
        PageInfo pageInfo = new PageInfo(list);
        return JSON.toJSONString(ResponseResult.getSuccessResult(pageInfo,"C200",null));
    }
}




