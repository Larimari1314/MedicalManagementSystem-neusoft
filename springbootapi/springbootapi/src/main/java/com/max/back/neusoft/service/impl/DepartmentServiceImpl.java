package com.max.back.neusoft.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.max.back.neusoft.pojo.Department;
import com.max.back.neusoft.service.DepartmentService;
import com.max.back.neusoft.dao.DepartmentMapper;
import org.springframework.stereotype.Service;

/**
* @author 86155
* @description 针对表【n_department】的数据库操作Service实现
* @createDate 2022-07-12 10:57:32
*/
@Service
public class DepartmentServiceImpl extends ServiceImpl<DepartmentMapper, Department>
    implements DepartmentService{

}




