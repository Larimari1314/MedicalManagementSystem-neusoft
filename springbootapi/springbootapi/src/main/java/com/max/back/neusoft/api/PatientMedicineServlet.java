package com.max.back.neusoft.api;

import com.max.back.neusoft.form.DispensingMedicineFrom;
import com.max.back.neusoft.service.PatientmedicineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/patientMedicine")
public class PatientMedicineServlet {
    @Autowired
    private PatientmedicineService patientmedicineService;

    @PostMapping("/findPatientMedic")
    public String findPatientMedic(@RequestBody DispensingMedicineFrom dispensingMedicineFrom) {
        System.out.println(dispensingMedicineFrom);
        return patientmedicineService.selectByUsernameAndDoctorName(dispensingMedicineFrom);
    }
    @PostMapping("/viewListMedicines")
    public String viewListMedicines(@RequestBody String id) {
        id=id.replace("=","");
        return patientmedicineService.findOrderDrug(id);
    }

}
