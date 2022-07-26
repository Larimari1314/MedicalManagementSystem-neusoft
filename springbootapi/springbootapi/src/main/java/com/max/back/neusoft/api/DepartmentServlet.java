package com.max.back.neusoft.api;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.max.back.common.http.ResponseResult;
import com.max.back.neusoft.form.DepartmentFindFrom;
import com.max.back.neusoft.form.DepartmentSaveForm;
import com.max.back.neusoft.pojo.Department;
import com.max.back.neusoft.service.DepartmentService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;

/**
 * 科室
 */
@CrossOrigin
@RestController
@RequestMapping("/department")
public class DepartmentServlet {
    private static MultipartFile multipartFile=null;
    private static String newId;
    @Autowired
    private DepartmentService departmentService;

    /**
     * 根据规则查找科室
     * @param departmentFindFrom 根据此信息查找
     * @return
     */
    @ApiOperation(value = "根据规则查找科室")
    @PostMapping("/findDepartment")
    public String findDepartment(@RequestBody @Valid DepartmentFindFrom departmentFindFrom) {
        return departmentService.findDepartment(departmentFindFrom);
    }

    /**
     * 科室上传封面接收
     * @param file
     */
    @ApiOperation(value = "科室上传封面接收")
    @PostMapping("/avatar")
    public void avatar(@RequestBody MultipartFile file) {
        multipartFile=file;
    }

    /**
     * 更新科室
     * @param departmentSaveForm
     * @return
     * @throws IOException
     */
    @ApiOperation(value = "更新科室")
    @PostMapping("/updateDepartment")
    public String updateDepartment(@RequestBody @Valid DepartmentSaveForm departmentSaveForm) throws IOException {
        //更新，存储界面收到的图片
        if (multipartFile!=null) {
            String path = System.getProperty("user.dir");
            File newFile = new File(path + "\\src\\main\\webapp\\Img\\department\\" + departmentSaveForm.getId() + ".jpg");
            multipartFile.transferTo(newFile);
            multipartFile=null;
            //存储头像链接地址
            departmentSaveForm.setAvatar("http://localhost:8000/hospital/Img/department/"  + departmentSaveForm.getId() + ".jpg");
        }
        //更新信息
        UpdateWrapper<Department> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("d_id", departmentSaveForm.getId())
                .set("d_name", departmentSaveForm.getName())
                .set("d_introduce", departmentSaveForm.getIntroduce())
                .set("d_avatar", departmentSaveForm.getAvatar());
        if (departmentService.update(updateWrapper)) {
            return JSON.toJSONString(ResponseResult.getSuccessResult(null, "C200", null));
        } else {
            return JSON.toJSONString(ResponseResult.getErrorResult("C500"));
        }
    }

    /**
     * 添加科室
     * @param departmentSaveForm
     * @return
     * @throws IOException
     */
    @ApiOperation(value = "添加科室")
    @PostMapping("/addDepartment")
    public String addDepartment(@RequestBody @Valid DepartmentSaveForm departmentSaveForm) throws IOException {
        if(departmentService.getById(departmentSaveForm.getId())!=null){
            return JSON.toJSONString(ResponseResult.getErrorResult("C405"));
        }
        String path = System.getProperty("user.dir");
        File newFile = new File(path + "\\src\\main\\webapp\\Img\\department\\" + departmentSaveForm.getId() + ".jpg");
        multipartFile.transferTo(newFile);
        //存储头像链接地址
        departmentSaveForm.setAvatar("http://localhost:8000/hospital/Img/department/"  + departmentSaveForm.getId() + ".jpg");
        Department department = BeanUtil.toBean(departmentSaveForm, Department.class);
        System.out.println(department);
        if(departmentService.save(department)){
            return JSON.toJSONString(ResponseResult.getSuccessResult(null, "C200", null));
        }else {
            return JSON.toJSONString(ResponseResult.getErrorResult("C500"));
        }
    }
    /**
     * 查找科室id和名称
     * @return
     */
    @ApiOperation(value = "查找科室id和名称")
    @GetMapping("/findDepartment")
    public String findDepartment(){
        QueryWrapper<Department> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("d_id","d_name");
        return JSON.toJSONString( departmentService.listMaps(queryWrapper));
    }

}
