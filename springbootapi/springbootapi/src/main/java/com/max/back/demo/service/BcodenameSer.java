package com.max.back.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.max.back.demo.entity.Bcodename;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author max
 * @since 2021-07-29
 */
public interface BcodenameSer extends IService<Bcodename> {
    public List<Bcodename> queryByCodeType(String codeId);
}
