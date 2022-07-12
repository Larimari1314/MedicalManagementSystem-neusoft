package com.max.back.neusoft.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.max.back.neusoft.pojo.Doctor;
import com.max.back.neusoft.service.DoctorService;
import com.max.back.neusoft.mapper.DoctorMapper;
import org.springframework.stereotype.Service;

/**
* @author 86155
* @description 针对表【n_doctor】的数据库操作Service实现
* @createDate 2022-07-12 10:58:14
*/
@Service
public class DoctorServiceImpl extends ServiceImpl<DoctorMapper, Doctor>
    implements DoctorService{

}




