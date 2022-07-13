package com.max.back.neusoft.api;

import com.max.back.neusoft.service.GeneralitemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/generalitem")
public class GeneralitemServlet {
    @Autowired
    private GeneralitemService generalitemService;
    //查找性别
    @GetMapping("/findGender")
    public String findGender(){
        return generalitemService.findGender();
    }
}
