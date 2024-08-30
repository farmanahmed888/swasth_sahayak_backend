package com.example.demo.Controller.Patient;

import com.example.demo.DTO.Doctor.doctordetailsDTO;
import com.example.demo.DTO.Patient.PatientdetailDTO;
import com.example.demo.Entity.doctor.doctorDetails;
import com.example.demo.Entity.patient.PatientDetails;
import com.example.demo.Repository.Doctor.DoctorEncryptedRepository;
import com.example.demo.Repository.Doctor.DoctorRepository;
import com.example.demo.Repository.Patient.patientDetailsRepository;
import jakarta.persistence.AttributeConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.EncDec.Encrypt;

import java.util.List;

@RequestMapping("/patientDetails")
@RestController
public class PatientDetailsController {
//    @Autowired
//    private patientDetailsRepository patientdetailsRepository;
//
//
//    @GetMapping("/getpatientdetails/{id}")
//    public PatientdetailDTO getDetails(@PathVariable String id) {
//        List<PatientDetails> pa=patientdetailsRepository.findAll();
//        PatientdetailDTO ps = new PatientdetailDTO();
//        for(PatientDetails it:pa){
//            System.out.println(it.getAbhaid());
//            if(it.getAbhaid().equals(id)){
//                ps.setName(it.getName());
//                ps.setMobileno(it.getMobileno());
//            }
//        }
//        return ps;
//    }
}