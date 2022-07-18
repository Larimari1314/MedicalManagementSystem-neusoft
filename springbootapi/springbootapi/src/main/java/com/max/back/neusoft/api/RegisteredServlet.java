package com.max.back.neusoft.api;

import com.max.back.neusoft.form.RegisteredFrom;
import com.max.back.neusoft.form.RegisteredUpdateFrom;
import com.max.back.neusoft.service.RegisteredService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/registered")
public class RegisteredServlet {
    @Autowired
    private RegisteredService registeredService;

    //根据名称返回挂号列表
    @PostMapping("/findByName")
    public String findByName(@RequestBody RegisteredFrom registeredFrom) {
        return registeredService.findAll(registeredFrom);
    }

    //更新价格，采用乐观锁机制进行更新
    @PostMapping("/updatePrice")
    public String updatePrice(@RequestBody @Valid RegisteredUpdateFrom registeredUpdateFrom) {
        return registeredService.updatePrice(registeredUpdateFrom);
    }

}
