package com.max.back.neusoft.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.max.back.neusoft.pojo.Orderdrug;
import com.max.back.neusoft.service.OrderdrugService;
import com.max.back.neusoft.dao.OrderdrugMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author 86155
* @description 针对表【n_orderDrug】的数据库操作Service实现
* @createDate 2022-07-17 07:10:40
*/
@Service
public class OrderdrugServiceImpl extends ServiceImpl<OrderdrugMapper, Orderdrug>
    implements OrderdrugService{
    @Autowired
    private OrderdrugMapper orderdrugMapper;
    @Override
    public boolean createSameOrder(String oldId, String newId) {
        QueryWrapper<Orderdrug> querywrapper=new QueryWrapper<>();
        querywrapper.eq("o_id", oldId);
        List<Orderdrug> orderdrugs = orderdrugMapper.selectList(querywrapper);
        if(orderdrugs==null){
            return true;
        }
        orderdrugs.stream().forEach(
                s->{
                    s.setId(newId);
                    orderdrugMapper.insert(s);
                }
        );
        return true;
    }
}




