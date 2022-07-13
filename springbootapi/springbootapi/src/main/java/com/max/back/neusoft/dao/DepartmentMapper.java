package com.max.back.neusoft.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.max.back.neusoft.form.DepartmentFindFrom;
import com.max.back.neusoft.pojo.Department;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author 86155
 * @description 针对表【n_department】的数据库操作Mapper
 * @createDate 2022-07-12 11:38:45
 * @Entity com.max.back.neusoft.pojo.Department
 */
@Mapper
@Repository
public interface DepartmentMapper extends BaseMapper<Department> {
    public List<Map<String, Object>> findDepartment(DepartmentFindFrom departmentFindFrom);
}




