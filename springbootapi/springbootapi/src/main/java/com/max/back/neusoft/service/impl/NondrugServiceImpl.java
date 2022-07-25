package com.max.back.neusoft.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.max.back.common.http.ResponseResult;
import com.max.back.common.sercurity.utils.export.ExportDataUtils;
import com.max.back.neusoft.dao.NondrugMapper;
import com.max.back.neusoft.form.DrugFindFrom;
import com.max.back.neusoft.pojo.Nondrug;
import com.max.back.neusoft.service.NondrugService;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author 86155
 * @description 针对表【n_nonDrug】的数据库操作Service实现
 * @createDate 2022-07-12 11:48:31
 */
@Service
public class NondrugServiceImpl extends ServiceImpl<NondrugMapper, Nondrug>
        implements NondrugService {
    @Autowired
    private NondrugMapper nondrugMapper;

    @Override
    public String getAllDrugByRequire(DrugFindFrom drugFindFrom) {
        PageHelper.startPage(drugFindFrom.getPage(), 10);
        List<Map<String, Object>> allDrugByRequire = nondrugMapper.getAllDrugByRequire(drugFindFrom);
        PageInfo pageInfo = new PageInfo(allDrugByRequire);
        return JSON.toJSONString(ResponseResult.getSuccessResult(pageInfo, "C200", null), SerializerFeature.DisableCircularReferenceDetect);
    }

    @Override
    public ByteArrayOutputStream drugTemplateDownload(String url) {
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
    public ByteArrayOutputStream drugExportData(String url) {
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
            List<Nondrug> list = list();
            //将allDoctor写入数据中
            XSSFSheet sheet = map.get(xb);
            int row = 3;
            XSSFRow row_3 = null;
            for (Nondrug drug : list) {
                row_3 = sheet.createRow(row);
                XSSFCell cell_3_1 = row_3.createCell(1);
                cell_3_1.setCellStyle(cellStyle);
                cell_3_1.setCellValue(drug.getId());
                cell_3_1.setCellType(CellType.STRING);
                XSSFCell cell_3_2 = row_3.createCell(2);
                cell_3_2.setCellStyle(cellStyle);
                cell_3_2.setCellValue(drug.getCover());
                cell_3_2.setCellType(CellType.STRING);
                XSSFCell cell_3_3 = row_3.createCell(3);
                cell_3_3.setCellStyle(cellStyle);
                cell_3_3.setCellValue(drug.getName());
                cell_3_3.setCellType(CellType.STRING);
                XSSFCell cell_3_4 = row_3.createCell(4);
                cell_3_4.setCellStyle(cellStyle);
                cell_3_4.setCellValue(drug.getSpecification());
                cell_3_4.setCellType(CellType.STRING);
                XSSFCell cell_3_5 = row_3.createCell(5);
                cell_3_5.setCellStyle(cellStyle);
                cell_3_5.setCellValue(drug.getNumber());
                cell_3_5.setCellType(CellType.STRING);
                XSSFCell cell_3_6 = row_3.createCell(6);
                cell_3_6.setCellStyle(cellStyle);
                cell_3_6.setCellValue(drug.getPrice());
                cell_3_6.setCellType(CellType.STRING);
                XSSFCell cell_3_7 = row_3.createCell(7);
                cell_3_7.setCellStyle(cellStyle);
                cell_3_7.setCellValue(drug.getDeletes());
                cell_3_7.setCellType(CellType.STRING);
                XSSFCell cell_3_8 = row_3.createCell(8);
                cell_3_8.setCellStyle(cellStyle);
                cell_3_8.setCellValue(drug.getEnable());
                cell_3_8.setCellType(CellType.STRING);
                XSSFCell cell_3_9 = row_3.createCell(9);
                cell_3_9.setCellStyle(cellStyle);
                cell_3_9.setCellValue(drug.getVersion());
                cell_3_9.setCellType(CellType.STRING);
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
        ArrayList<Nondrug> nondrugs = new ArrayList<>();
        while (true) {
            Nondrug nondrug = new Nondrug();
            XSSFRow new_row = sheetAt.getRow(newRow);
            try {
                new_row.getCell(1).setCellType(CellType.STRING);
                String rawValue = new_row.getCell(1).getStringCellValue();
                if (!StringUtils.isNotBlank(rawValue) || rawValue.equals("")) {
                    break;
                }
                nondrug.setId(rawValue);
                new_row.getCell(2).setCellType(CellType.STRING);
                nondrug.setCover(new_row.getCell(2).getStringCellValue());
                new_row.getCell(3).setCellType(CellType.STRING);
                nondrug.setName(new_row.getCell(3).getStringCellValue());
                new_row.getCell(4).setCellType(CellType.STRING);
                nondrug.setSpecification(new_row.getCell(4).getStringCellValue());
                new_row.getCell(5).setCellType(CellType.STRING);
                nondrug.setNumber(Integer.parseInt(new_row.getCell(5).getStringCellValue()));
                new_row.getCell(6).setCellType(CellType.STRING);
                nondrug.setPrice(Integer.parseInt(new_row.getCell(6).getStringCellValue()));
                new_row.getCell(7).setCellType(CellType.STRING);
                nondrug.setDeletes(Integer.parseInt(new_row.getCell(7).getStringCellValue()));
                new_row.getCell(8).setCellType(CellType.STRING);
                nondrug.setEnable(Integer.parseInt(new_row.getCell(8).getStringCellValue()));
                new_row.getCell(9).setCellType(CellType.STRING);
                nondrug.setVersion(Integer.parseInt(new_row.getCell(9).getStringCellValue()));
                nondrugs.add(nondrug);
                newRow++;
            } catch (NullPointerException e) {
                break;
                //抱空指针异常表明数据读取完，跳出循环即可
            } catch (Exception e) {
//                e.printStackTrace();
                return JSON.toJSONString(ResponseResult.getErrorResult("C500"));
            } finally {
                //删除源文件
                if (!file.delete()) {
                    file.delete();
                }
            }
        }

        try {
            //保存数据
            saveBatch(nondrugs);
        } catch (Exception e) {
            //原因分析为数据可能已存在
            return JSON.toJSONString(ResponseResult.getErrorResult("C501"));
        } finally {
            //删除源文件
            if (!file.delete()) {
                file.delete();
            }
        }
        //上述没报错误就是导入成功了
        return JSON.toJSONString(ResponseResult.getSuccessResult(null, "C200", null));
    }

    @Override
    public String getDeletes() {
        List<Map<String, Object>> deletes = nondrugMapper.getDeletes();
        return JSON.toJSONString(ResponseResult.getSuccessResult(deletes, "C200", null));
    }

    @Override
    public String deletePermanently(String id) {
        Integer integer = nondrugMapper.deletePermanently(id);
        if (integer != 0) {
            return JSON.toJSONString(ResponseResult.getSuccessResult(null, "C200", null), SerializerFeature.DisableCircularReferenceDetect);
        } else {
            return JSON.toJSONString(ResponseResult.getErrorResult("C500"));
        }
    }

    @Override
    public String dataRecovery(String id) {
        Integer integer = nondrugMapper.dataRecovery(id);
        if (integer != 0) {
            return JSON.toJSONString(ResponseResult.getSuccessResult(null, "C200", null), SerializerFeature.DisableCircularReferenceDetect);
        } else {
            return JSON.toJSONString(ResponseResult.getErrorResult("C500"));
        }
    }
}




