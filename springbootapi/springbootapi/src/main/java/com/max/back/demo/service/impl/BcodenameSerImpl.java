package com.max.back.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.max.back.demo.dao.BcodenameDao;
import com.max.back.demo.entity.Bcodename;
import com.max.back.demo.service.BcodenameSer;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author max
 * @since 2021-07-29
 */
@Service
public class BcodenameSerImpl extends ServiceImpl<BcodenameDao, Bcodename> implements BcodenameSer {
    @Override
    public List<Bcodename> queryByCodeType(String codeId) {
        return query().eq("codeid", codeId).list();
    }
}
