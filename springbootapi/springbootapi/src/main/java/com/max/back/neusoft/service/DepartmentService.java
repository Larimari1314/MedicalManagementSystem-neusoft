package com.max.back.neusoft.service;

import com.max.back.neusoft.form.DepartmentFindFrom;
import com.max.back.neusoft.pojo.Department;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author 86155
* @description 针对表【n_department】的数据库操作Service
* @createDate 2022-07-12 11:38:45
*/
public interface DepartmentService extends IService<Department> {

    String findDepartment(DepartmentFindFrom departmentFindFrom);
}
