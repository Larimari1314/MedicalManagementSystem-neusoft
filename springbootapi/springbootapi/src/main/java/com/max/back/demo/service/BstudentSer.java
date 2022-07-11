package com.max.back.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.max.back.demo.entity.Bstudent;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author max
 * @since 2021-07-29
 */
public interface BstudentSer extends IService<Bstudent> {
    public PageInfo<Map<String, Object>> queryPagerByCond(String stuName,
                                                          String schId,
                                                          String sexCode,
                                                          int pageNum,
                                                          int pageSize);
    public boolean checkAndInsert(Bstudent bstudent);
    //public List<Bstudent> myget();
}
