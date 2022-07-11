package com.max.back.demo.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.max.back.demo.entity.Bstudent;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author max
 * @since 2021-07-29
 */
public interface BstudentDao extends BaseMapper<Bstudent> {

    public List<Map<String, Object>> selectByCond(@Param("stuName") String stuName,
                                                  @Param("schId") String schId,
                                                  @Param("sexCode") String sexCode);


    public Map<String, String> selectMaxStuCode();

    public List<Bstudent> getmstu();
}
