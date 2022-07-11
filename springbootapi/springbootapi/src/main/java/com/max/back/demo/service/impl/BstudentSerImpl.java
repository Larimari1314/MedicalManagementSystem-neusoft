package com.max.back.demo.service.impl;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.max.back.demo.dao.BstudentDao;
import com.max.back.demo.entity.Bstudent;
import com.max.back.demo.service.BstudentSer;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author max
 * @since 2021-07-29
 */
@Service
public class BstudentSerImpl extends ServiceImpl<BstudentDao, Bstudent> implements BstudentSer {

    @Override
    public PageInfo<Map<String, Object>> queryPagerByCond(String stuName, String schId, String sexCode, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Map<String, Object>> list = baseMapper.selectByCond(stuName, schId, sexCode);
        //利用PageInfo分页
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

//    @Override
//    public List<Bstudent> myget() {
//        return baseMapper.getmstu();
//    }

    public boolean checkAndInsert(Bstudent bstudent) {
        Map<String, String> stuCodeMaxMap = baseMapper.selectMaxStuCode();
        String stuCodeMax = stuCodeMaxMap.get("maxstucode");
        if (StrUtil.isEmpty(stuCodeMax)) {
            bstudent.setStucode("A01");
        } else {
            String stuCodeMaxNum = stuCodeMax.substring(1);
            System.out.println(stuCodeMaxNum);
            Integer stuCodeMaxNumInt = Integer.parseInt(stuCodeMaxNum);
            String lastFix = NumberUtil.decimalFormat("00", ++stuCodeMaxNumInt);
            bstudent.setStucode("A" + lastFix);
        }
        bstudent.setId(UUID.randomUUID().toString());
        return save(bstudent);
    }
}
