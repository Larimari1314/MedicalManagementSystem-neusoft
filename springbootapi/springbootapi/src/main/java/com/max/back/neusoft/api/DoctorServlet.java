package com.max.back.neusoft.api;

import com.max.back.neusoft.form.DoctorRegisteredFrom;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/doctor")
public class DoctorServlet {
    @PostMapping("/findByRegistered")
    public String findByRegistered(@RequestBody DoctorRegisteredFrom doctorRegisteredFrom) {

        return "";
    }
}
