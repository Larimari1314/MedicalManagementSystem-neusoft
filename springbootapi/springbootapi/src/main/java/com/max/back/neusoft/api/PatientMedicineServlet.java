package com.max.back.neusoft.api;

import cn.hutool.core.date.DateTime;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.max.back.common.http.ResponseResult;
import com.max.back.neusoft.form.DispensingMedicineFrom;
import com.max.back.neusoft.pojo.Patientmedicine;
import com.max.back.neusoft.service.OrderdrugService;
import com.max.back.neusoft.service.PatientmedicineService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;

@CrossOrigin
@RestController
@RequestMapping("/patientMedicine")
public class PatientMedicineServlet {
    @Autowired
    private PatientmedicineService patientmedicineService;
    @Autowired
    private OrderdrugService orderdrugService;
    /**
     * 根据信息查找发药列表
     * @param dispensingMedicineFrom
     * @return
     */
    @ApiOperation(value = "根据信息查找发药列表")
    @PostMapping("/findPatientMedic")
    public String findPatientMedic(@RequestBody DispensingMedicineFrom dispensingMedicineFrom) {
        return patientmedicineService.selectByUsernameAndDoctorName(dispensingMedicineFrom);
    }

    /**
     * 根据订单id查看所属药物
     * @param id
     * @return
     */
    @ApiOperation(value = "根据订单id查看所属药物")
    @PostMapping("/viewListMedicines")
    public String viewListMedicines(@RequestBody String id) {
        id = id.replace("=", "");
        return patientmedicineService.findOrderDrug(id);
    }

    /**
     * 退费操作
     * @param id
     * @return
     */
    @ApiOperation(value = "退费操作")
    @PostMapping("/withdrawal")
    public String withdrawal(@RequestBody String id) {
        id = id.replace("=", "");
        QueryWrapper<Patientmedicine> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("p_id", id);
        //检测是否是发药状态
        Patientmedicine one = patientmedicineService.getOne(queryWrapper);
        if (!one.getState().equals("G009")) {
            return JSON.toJSONString(ResponseResult.getErrorResult("C405"));
        }
        //已发放则更新信息
        UpdateWrapper<Patientmedicine> updateWrapper = new UpdateWrapper<>();
        DateTime dateTime=new DateTime();
        updateWrapper.eq("p_id", id)
                .set("p_state", "G010")
                .set("p_date", dateTime);
        boolean update = patientmedicineService.update(updateWrapper);
        if (update) {
            return JSON.toJSONString(ResponseResult.getSuccessResult(null, "C200", null), SerializerFeature.DisableCircularReferenceDetect);
        } else {
            return JSON.toJSONString(ResponseResult.getErrorResult("C500"), SerializerFeature.DisableCircularReferenceDetect);
        }
    }
    @PostMapping("/orderDetails")
    public String orderDetails(@RequestBody String id){
        id = id.replace("=", "");
        return patientmedicineService.orderDetails(id);
    }

    /**
     * 根据id创建相同订单
     * @param id
     * @return
     */
    @Transactional
    @ApiOperation(value = "根据id创建相同订单")
    @PostMapping("/createSameOrder")
    public String createSameOrder(@RequestBody String id){
        id=id.replace("=","");
        Patientmedicine patientmedicine = patientmedicineService.getById(id);
        if(patientmedicine==null||!patientmedicine.getState().equals("G010")){
            return JSON.toJSONString(ResponseResult.getErrorResult("C404"));
        }
        //创建订单id 根据时间yyyyMMdd+用户id构成，如果存在相同id抱C405错误
        Date date=new Date();
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyyMMdd");
        String sameId = simpleDateFormat.format(date)+patientmedicine.getUserid();
        //查询id是否存在
        if(patientmedicineService.getById(sameId)!=null){
            return JSON.toJSONString(ResponseResult.getErrorResult("C405"));
        }
        //上述条件达成即可创建订单
        patientmedicine.setDate(new Date());
        patientmedicine.setId(sameId);
        patientmedicine.setState("G008");
        //保存到订单表
        boolean save = patientmedicineService.save(patientmedicine);
        //订单药品表也需保存
        boolean sameOrder = orderdrugService.createSameOrder(id, sameId);
        if (save&&sameOrder) {
            return JSON.toJSONString(ResponseResult.getSuccessResult(sameId, "C200", null), SerializerFeature.DisableCircularReferenceDetect);
        } else {
            return JSON.toJSONString(ResponseResult.getErrorResult("C500"), SerializerFeature.DisableCircularReferenceDetect);
        }
    }
    @GetMapping("/getDayNumber")
    public String getDayNumber(){
        return patientmedicineService.getDayMedicine();
    }
    @GetMapping("/getAllValues")
    public String getAllValues(){
        return patientmedicineService.getAllValues();
    }
    @GetMapping("/getStateStatistics")
    public String getStateStatistics(){
        return patientmedicineService.getStateStatistics();
    }
}

