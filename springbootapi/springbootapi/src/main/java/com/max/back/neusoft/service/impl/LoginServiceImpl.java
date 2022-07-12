package com.max.back.neusoft.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.max.back.neusoft.pojo.Login;
import com.max.back.neusoft.service.LoginService;
import com.max.back.neusoft.dao.LoginMapper;
import org.springframework.stereotype.Service;

/**
* @author 86155
* @description 针对表【n_login】的数据库操作Service实现
* @createDate 2022-07-12 11:46:37
*/
@Service
public class LoginServiceImpl extends ServiceImpl<LoginMapper, Login>
    implements LoginService{

}




