package com.max.back.neusoft.dao;

import com.max.back.neusoft.form.UserFindFrom;
import com.max.back.neusoft.pojo.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
* @author 86155
* @description 针对表【n_user】的数据库操作Mapper
* @createDate 2022-07-12 11:55:22
* @Entity com.max.back.neusoft.pojo.User
*/@Mapper
@Repository
public interface UserMapper extends BaseMapper<User> {
    public List<Map<String,Object>> findUser(UserFindFrom userFindFrom);

    List<Map<String,Object>> getDeletes();

    public Integer deletePermanently(String id);

    public Integer dataRecovery(String id);
    public List<Map<String,Object>> mainGetNumber();
}




