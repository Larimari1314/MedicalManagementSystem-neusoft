package com.max.back.neusoft.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.max.back.common.http.ResponseResult;
import com.max.back.common.sercurity.utils.export.ExportDataUtils;
import com.max.back.neusoft.form.UserFindFrom;
import com.max.back.neusoft.pojo.Patientmedicine;
import com.max.back.neusoft.pojo.User;
import com.max.back.neusoft.service.PatientmedicineService;
import com.max.back.neusoft.service.UserService;
import com.max.back.neusoft.dao.UserMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

/**
* @author 86155
* @description 针对表【n_user】的数据库操作Service实现
* @createDate 2022-07-12 11:55:22
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PatientmedicineService patientmedicineService;
    @Override
    public String findUser(UserFindFrom userFindFrom) {
        try {
            PageHelper.startPage(userFindFrom.getPage(), 20);
            List<Map<String, Object>> list = userMapper.findUser(userFindFrom);
            //将时间转化成字符串形式
            for (Map<String, Object> stringObjectMap : list) {
                String brithday = stringObjectMap.get("birthday").toString();
                stringObjectMap.remove("birthday");
                stringObjectMap.put("birthday",brithday);
            }
            PageInfo pageInfo = new PageInfo(list);
            return JSON.toJSONString(ResponseResult.getSuccessResult(pageInfo,"C200",null), SerializerFeature.DisableCircularReferenceDetect);
        }catch (Exception e){
            return JSON.toJSONString(ResponseResult.getErrorResult("C500"));
        }
    }

    @Override
    public ByteArrayOutputStream doctorTemplateDownload(String url) {
        ByteArrayOutputStream ios = new ByteArrayOutputStream();
        try {
            Map<XSSFWorkbook, XSSFSheet> map = ExportDataUtils.excelTemplate(url);
            Set<XSSFWorkbook> xssfWorkbooks = map.keySet();
            XSSFWorkbook xb = null;
            for (XSSFWorkbook xssfWorkbook : xssfWorkbooks) {
                xb = xssfWorkbook;
            }
            CellStyle cellStyle = xb.createCellStyle();
            cellStyle.setAlignment(HorizontalAlignment.CENTER);
            cellStyle.setBorderBottom(BorderStyle.THIN);
            cellStyle.setBorderLeft(BorderStyle.THIN);
            cellStyle.setBorderRight(BorderStyle.THIN);
            cellStyle.setBorderTop(BorderStyle.THIN);
            //将allDoctor写入数据中
            XSSFSheet sheet = map.get(xb);
            xb.write(ios);
            xb.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return ios;
    }

    @Override
    public ByteArrayOutputStream userExportData(String url) {
        ByteArrayOutputStream ios = new ByteArrayOutputStream();
        try {
            Map<XSSFWorkbook, XSSFSheet> map = ExportDataUtils.excelTemplate(url);
            Set<XSSFWorkbook> xssfWorkbooks = map.keySet();
            XSSFWorkbook xb = null;
            for (XSSFWorkbook xssfWorkbook : xssfWorkbooks) {
                xb = xssfWorkbook;
            }
            CellStyle cellStyle = xb.createCellStyle();
            cellStyle.setAlignment(HorizontalAlignment.CENTER);
            cellStyle.setBorderBottom(BorderStyle.THIN);
            cellStyle.setBorderLeft(BorderStyle.THIN);
            cellStyle.setBorderRight(BorderStyle.THIN);
            cellStyle.setBorderTop(BorderStyle.THIN);
            List<User> userList = list();
            //将allDoctor写入数据中
            XSSFSheet sheet = map.get(xb);
            int row = 3;
            XSSFRow row_3 = null;
            for (User user : userList) {
                row_3 = sheet.createRow(row);
                XSSFCell cell_3_1 = row_3.createCell(1);
                cell_3_1.setCellStyle(cellStyle);
                cell_3_1.setCellValue(user.getId());
                cell_3_1.setCellType(CellType.STRING);
                XSSFCell cell_3_2 = row_3.createCell(2);
                cell_3_2.setCellStyle(cellStyle);
                cell_3_2.setCellValue(user.getUsername());
                cell_3_2.setCellType(CellType.STRING);
                XSSFCell cell_3_3 = row_3.createCell(3);
                cell_3_3.setCellStyle(cellStyle);
                cell_3_3.setCellValue(user.getAge());
                cell_3_3.setCellType(CellType.STRING);
                XSSFCell cell_3_4 = row_3.createCell(4);
                cell_3_4.setCellStyle(cellStyle);
                cell_3_4.setCellValue(user.getBirthday());
                cell_3_4.setCellType(CellType.STRING);
                XSSFCell cell_3_5 = row_3.createCell(5);
                cell_3_5.setCellStyle(cellStyle);
                cell_3_5.setCellValue(user.getIdentitynumber());
                cell_3_5.setCellType(CellType.STRING);
                XSSFCell cell_3_6 = row_3.createCell(6);
                cell_3_6.setCellStyle(cellStyle);
                cell_3_6.setCellValue(user.getAvatar());
                cell_3_6.setCellType(CellType.STRING);
                XSSFCell cell_3_7 = row_3.createCell(7);
                cell_3_7.setCellStyle(cellStyle);
                cell_3_7.setCellValue(user.getSex());
                cell_3_7.setCellType(CellType.STRING);
                XSSFCell cell_3_8 = row_3.createCell(8);
                cell_3_8.setCellStyle(cellStyle);
                cell_3_8.setCellValue(user.getDeletes());
                cell_3_8.setCellType(CellType.STRING);
                row++;
            }
            xb.write(ios);
            xb.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return ios;
    }

    @Override
    public String analyseFile(String fileName) {
        String path = System.getProperty("user.dir");
        File file = new File(path + "\\src\\main\\webapp\\static\\" + fileName + ".xlsx");
        XSSFWorkbook xssfWorkbook = null;
        try {
            xssfWorkbook = new XSSFWorkbook(file);
        } catch (IOException | InvalidFormatException e) {
            throw new RuntimeException(e);
        }
        //获取工作谱对象
        XSSFSheet sheetAt = xssfWorkbook.getSheetAt(0);
        int newRow = 3;
        ArrayList<User> userList = new ArrayList<>();
        while (true) {
            User user = new User();
            XSSFRow new_row = sheetAt.getRow(newRow);
            try {
                String rawValue = new_row.getCell(1).getRawValue();
                if (!StringUtils.isNotBlank(rawValue) || rawValue.equals("")) {
                    break;
                }
                user.setId(rawValue);
                user.setUsername(new_row.getCell(2).getStringCellValue());
                user.setAge(Integer.parseInt(new_row.getCell(3).getStringCellValue()));
                user.setBirthday(new_row.getCell(4).getDateCellValue());
                user.setIdentitynumber(new_row.getCell(5).getStringCellValue());
                user.setAvatar(new_row.getCell(6).getStringCellValue());
                user.setSex(new_row.getCell(7).getStringCellValue());
                user.setDeletes(Integer.parseInt(new_row.getCell(8).getStringCellValue()));
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                userList.add(user);
                newRow++;
            } catch (NullPointerException e) {
                break;
                //抱空指针异常表明数据读取完，跳出循环即可
            } catch (Exception e) {
                return JSON.toJSONString(ResponseResult.getErrorResult("C500"));
            }finally {
                //删除源文件
                if(!file.delete()){
                    file.delete();
                }
            }
        }

        try {
            //保存数据
            saveBatch(userList);
        } catch (Exception e) {
            //原因分析为数据可能已存在
            return JSON.toJSONString(ResponseResult.getErrorResult("C501"));
        }finally {
            //删除源文件
            if(!file.delete()){
                file.delete();
            }
        }
        //上述没报错误就是导入成功了
        return JSON.toJSONString(ResponseResult.getSuccessResult(null,"C200", null));
    }
    @Override
    public String getDeletes() {
        List<Map<String, Object>> deletes = userMapper.getDeletes();
        return JSON.toJSONString(ResponseResult.getSuccessResult(deletes,"C200",null));
    }

    @Override
    public String deletePermanently(String id) {
        QueryWrapper<Patientmedicine> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("p_userId", id);
        if (patientmedicineService.getMap(queryWrapper) != null) {
            return JSON.toJSONString(ResponseResult.getErrorResult("C405"));
        }
        Integer integer = userMapper.deletePermanently(id);
        if(integer!=0){
            return JSON.toJSONString(ResponseResult.getSuccessResult(null, "C200", null), SerializerFeature.DisableCircularReferenceDetect);
        }else {
            return JSON.toJSONString(ResponseResult.getErrorResult("C500"));
        }
    }

    @Override
    public String dataRecovery(String id) {
        Integer integer = userMapper.dataRecovery(id);
        if(integer!=0){
            return JSON.toJSONString(ResponseResult.getSuccessResult(null, "C200", null), SerializerFeature.DisableCircularReferenceDetect);
        }else {
            return JSON.toJSONString(ResponseResult.getErrorResult("C500"));
        }
    }

    /**
     * 首页获取医生患者数量
     * @return
     */
    @Override
    public String mainGetNumber() {
        return JSON.toJSONString(userMapper.mainGetNumber());
    }
}




