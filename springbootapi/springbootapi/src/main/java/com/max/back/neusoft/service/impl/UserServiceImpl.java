package com.max.back.neusoft.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.max.back.neusoft.pojo.User;
import com.max.back.neusoft.service.UserService;
import com.max.back.neusoft.mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
* @author 86155
* @description 针对表【n_user】的数据库操作Service实现
* @createDate 2022-07-12 11:01:03
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

}




