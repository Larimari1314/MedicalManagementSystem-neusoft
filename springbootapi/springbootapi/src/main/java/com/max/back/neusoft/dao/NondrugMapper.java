package com.max.back.neusoft.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.max.back.neusoft.form.DrugFindFrom;
import com.max.back.neusoft.pojo.Nondrug;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author 86155
 * @description 针对表【n_nonDrug】的数据库操作Mapper
 * @createDate 2022-07-12 11:48:31
 * @Entity com.max.back.neusoft.pojo.Nondrug
 */
@Mapper
@Repository
public interface NondrugMapper extends BaseMapper<Nondrug> {
    public abstract List<Map<String, Object>>  getAllDrugByRequire(DrugFindFrom drugFindFrom);
   List<Map<String,Object>> getDeletes();

    public Integer deletePermanently(String id);

    public Integer dataRecovery(String id);
}




