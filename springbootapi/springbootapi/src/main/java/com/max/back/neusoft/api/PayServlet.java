package com.max.back.neusoft.api;

import cn.hutool.core.date.DateTime;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.max.back.common.http.ResponseResult;
import com.max.back.common.sercurity.utils.QRcodeZxingUtil2;
import com.max.back.neusoft.form.PayFrom;
import com.max.back.neusoft.pojo.Patientmedicine;
import com.max.back.neusoft.service.PatientmedicineService;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.File;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 支付
 */
@CrossOrigin
@RestController
@RequestMapping("/pay")
public class PayServlet {
    //判断前端展示得价格和后端计算出来的价格是否对的上
    @Autowired
    private PatientmedicineService patientmedicineService;
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 数据校验，检测待支付订单得订单状态是否是未发放药物状态
     *
     * @param payFrom
     * @return
     */
    @ApiOperation(value = "数据校验")
    @PostMapping("/dataValidation")
    public String dataValidation(@RequestBody @Valid PayFrom payFrom) {
        Integer totalPrice = patientmedicineService.getTotalPrice(payFrom.getId());
        //价格对不上
        if (!Objects.equals(totalPrice, payFrom.getTotalPrice())) {
            return JSON.toJSONString(ResponseResult.getErrorResult("C503"));
        }
        //药品已发放
        QueryWrapper<Patientmedicine> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("p_id", payFrom.getId());
        Patientmedicine one = patientmedicineService.getOne(queryWrapper);
        if (!StringUtils.isNotBlank(one.getState()) || !one.getState().equals("G008")) {
            return JSON.toJSONString(ResponseResult.getErrorResult("C504"));
        }
        //添加药品价格信息
        UpdateWrapper<Patientmedicine> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("p_id", payFrom.getId())
                .set("p_price", payFrom.getTotalPrice());
        patientmedicineService.update(updateWrapper);
        //价格对的上，创建加密数据和id对应
        String s = UUID.randomUUID().toString();
        s = s.substring(0, 8) + s.substring(9, 13) + s.substring(14, 18) + s.substring(19, 23) + s.substring(24);
        String substring = s.substring(0, 24);
        //存储五分钟
        redisTemplate.boundValueOps(substring).set(payFrom.getId(), 5, TimeUnit.MINUTES);
        //创建二维码
        QRcodeZxingUtil2.greatePaymentLink("http://192.168.1.106:8080/#/paying/" + s, substring);
        return JSON.toJSONString(ResponseResult.getSuccessResult(substring, "C200", null));
    }

    /**
     * 通过付款id查找药品信息，并返回页面
     *
     * @param payId
     * @return
     */
    @ApiOperation(value = "通过付款id查找药品信息")
    @PostMapping("/paymentStatement")
    public String paymentStatement(@RequestBody String payId) {
        payId = payId.replace("=", "");
        //获取payId，通过redis获取数值
        String pid = (String) redisTemplate.boundValueOps(payId).get();
        if (!StringUtils.isNotBlank(pid)) {
            return JSON.toJSONString(ResponseResult.getErrorResult("C404"));
        }
        return patientmedicineService.payList(pid);
    }

    /**
     * 进行付款操作
     *
     * @param payId
     * @return
     */
    @ApiOperation(value = "进行付款操作")
    @PostMapping("/pay")
    public String pay(@RequestBody String payId) {
        payId = payId.substring(0, 24);
        //获取payId，通过redis获取数值
        String pid = (String) redisTemplate.boundValueOps(payId).get();
        //订单超时
        if (!StringUtils.isNotBlank(pid)) {
            return JSON.toJSONString(ResponseResult.getErrorResult("C404"));
        }
        //查看订单当前状态，是否是未发放状态
        QueryWrapper<Patientmedicine> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("p_id", pid);
        Patientmedicine one = patientmedicineService.getOne(queryWrapper);
        if (!StringUtils.isNotBlank(one.getState()) || !one.getState().equals("G008")) {
            return JSON.toJSONString(ResponseResult.getErrorResult("C504"));
        }
        //付款，修改状态为已付款状态
        UpdateWrapper<Patientmedicine> updateWrapper = new UpdateWrapper<>();
        DateTime dateTime = new DateTime();
        updateWrapper.eq("p_id", pid)
                .set("p_state", "G009")
                .set("p_date", dateTime);
        boolean update = patientmedicineService.update(updateWrapper);
        if (update) {
            //删除生成得二维码
            String path = System.getProperty("user.dir");
            File File = new File(path + "\\src\\main\\webapp\\Img\\pay\\" + payId + "\\" + payId + ".jpg");
            File.delete();
            File Files = new File(path + "\\src\\main\\webapp\\Img\\pay\\" + payId);
            Files.delete();
            payId = payId + "status";
            //将扫描状态改为C200已付款状态
            redisTemplate.boundValueOps(payId).set("C200", 5, TimeUnit.MINUTES);
            return JSON.toJSONString(ResponseResult.getSuccessResult(null, "C200", null), SerializerFeature.DisableCircularReferenceDetect);
        } else {
            return JSON.toJSONString(ResponseResult.getErrorResult("C500"));
        }
    }

    @PostMapping("/paymentStatus")
    public String paymentStatus(@RequestBody String id) {
        id = id.replace("=", "");
        String status = (String) redisTemplate.boundValueOps(id).get();
        //根绝id获取当前用户扫描后的状态
        if (status != null && status.equals("C201")) {
            //已经扫描
            return JSON.toJSONString(ResponseResult.getSuccessResult(null, "C201", null));
        } else if (status != null && status.equals("C200")) {
            //已付款
            return JSON.toJSONString(ResponseResult.getSuccessResult(null, "C200", null));
        } else {
            //未扫描
            return JSON.toJSONString(ResponseResult.getErrorResult("C408"));
        }
    }

    /**
     * 用户扫描后执行方法将扫描状态改为C201
     *
     * @param payId
     * @return
     */
    @ApiOperation(value = "用户扫描执行方法")
    @PostMapping("/scanStatus")
    public String scanStatus(@RequestBody String payId) {
        payId = payId.substring(0, 24) + "status";
        redisTemplate.boundValueOps(payId).set("C201", 5, TimeUnit.MINUTES);
        return JSON.toJSONString(ResponseResult.getSuccessResult(null, "C200", null));
    }
}
