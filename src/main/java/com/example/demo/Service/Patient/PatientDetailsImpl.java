package com.example.demo.Service.Patient;

import com.example.demo.DTO.Patient.PatientdetailDTO;
import com.example.demo.Entity.patient.PatientDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
@Service
public class PatientDetailsImpl {
    @Autowired
    private com.example.demo.Repository.Patient.patientDetailsRepository patientdetailsRepository;
    public PatientDetails getDetails(@PathVariable String id) {
        List<PatientDetails> pa=patientdetailsRepository.findAll();
        for(PatientDetails it:pa){
            System.out.println(it.getAbhaid());
            if(it.getAbhaid().equals(id)){
              return it;
            }
        }
        return new PatientDetails();
    }
}
