package com.max.back.neusoft.api;

import com.max.back.neusoft.form.DoctorRegisteredFrom;
import com.max.back.neusoft.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/doctor")
public class DoctorServlet {
    @Autowired
    private DoctorService doctorService;
    @PostMapping("/findByRegistered")
    public String findByRegistered(@RequestBody DoctorRegisteredFrom doctorRegisteredFrom) {
        return  doctorService.findByRegis(doctorRegisteredFrom);
    }
}
