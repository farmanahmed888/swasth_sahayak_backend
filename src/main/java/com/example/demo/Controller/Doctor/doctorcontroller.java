
package com.example.demo.Controller.Doctor;

import com.example.demo.DTO.Commmon.patientMetaData;
import com.example.demo.DTO.Doctor.diaganoseDTO;
import com.example.demo.DTO.Doctor.doctordetailsDTO;
import com.example.demo.DTO.Doctor.recentlytreatedDTO;
import com.example.demo.DTO.Patient.PatientProfileDTO;
import com.example.demo.DTO.Patient.artifactsdetails;
import com.example.demo.DTO.Questionnare.ICD10andnameDTO;
import com.example.demo.DTO.Questionnare.getdiagnosedetail;
import com.example.demo.Entity.FollowUp.followUpData;
import com.example.demo.Entity.Questionnaire.diagnose_id_gen;
import com.example.demo.DTO.Prescription.addprecriptionDTO;
import com.example.demo.Entity.common_entities.ICD10_mapping;
import com.example.demo.Repository.CommonRepositories.ICD10mapping;
import com.example.demo.Repository.Questionnare.DiagnoseID;
import com.example.demo.DTO.Prescription.prescriptionDTO;
import com.example.demo.DTO.Prescription.searchbydiseaseDTO;
import com.example.demo.Entity.common_entities.patientDoctorMapping;
import com.example.demo.Entity.doctor.doctorDetails;
import com.example.demo.Entity.patient.Artifacts;
import com.example.demo.Entity.patient.PatientDetails;
import com.example.demo.Entity.patient.prescriptionTable;
import com.example.demo.Repository.CommonRepositories.patientDoctorRepository;
import com.example.demo.Repository.Doctor.DoctorEncryptedRepository;
import com.example.demo.Repository.Doctor.DoctorRepository;
import com.example.demo.Repository.Followup.followupdataRepository;
import com.example.demo.Repository.Patient.patientDetailsRepository;
import com.example.demo.Repository.Questionnare.QuestionSet;
import com.example.demo.Repository.field_worker_Repositary.fieldWorkerEncrpt;
import com.example.demo.Repository.field_worker_Repositary.fieldwokerDetailRepositary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


import javax.swing.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

//Role: Doctor
//doctor dashboard
@RestController
@RequestMapping("/doctor")
@CrossOrigin("*")
public class doctorcontroller {
  @Autowired
  private com.example.demo.Service.doctorservicelayer.doctor_service doctor_service ;
  @Autowired
  private com.example.demo.Repository.Patient.patientDetailsRepository patientDetailsRepository;

  @GetMapping("/top3/{username}/{d}")
  public ResponseEntity<List<patientMetaData>> findPatientsByCount(@PathVariable String username,@PathVariable Date d) {
    List<patientMetaData> p = doctor_service.findPatientsByCount(username,d);
    if (p != null) {
      return new ResponseEntity<>(p, HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

  }

  @GetMapping("/findall/{username}/{date}")
  public ResponseEntity<List<patientMetaData>> findAllPatients(@PathVariable String username,@PathVariable Date date) {
    List<patientMetaData> p = doctor_service.findAllPatients(username,date);
    if (p != null) {
      return new ResponseEntity<>(p, HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  //new count on doctor dashboard
  @GetMapping("/findcount/{did}")
  public ResponseEntity<Long> findCount(@PathVariable String did) {
    long i = doctor_service.findCount(did);
    if (i > 0) return new ResponseEntity<>(i, HttpStatus.OK);
    else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

  //old count
  @GetMapping("/findoldcount/{did}/{d}")
  public ResponseEntity<Long> findoldcount(@PathVariable String did, @PathVariable Date d) {
    long i = doctor_service.findoldcount(did, d);
    if (i > 0) return new ResponseEntity<>(i, HttpStatus.OK);
    else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

  //fetching doctor details from doctor username
  @GetMapping("/getdoctordetails/{id}")
  public ResponseEntity<doctordetailsDTO> getDetails(@PathVariable String id) {
    doctordetailsDTO d = doctor_service.getDetails(id);
    if (d != null) {
      return new ResponseEntity<>(d, HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @GetMapping("/patientdashboard/{patientid}")
  public ResponseEntity<PatientProfileDTO> patientdashboard(@PathVariable int patientid) {
    PatientProfileDTO p = doctor_service.patientdashboard(patientid);
    if (p != null) {
      return new ResponseEntity<>(p, HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @GetMapping("/searchbydisease/{diseasename}/{id}")
  public ResponseEntity<List<searchbydiseaseDTO>> searchbydisease(@PathVariable String diseasename, @PathVariable String id) {
    List<searchbydiseaseDTO> d = doctor_service.searchbydisease(diseasename, id);
    if (d != null) {
      return new ResponseEntity<>(d, HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @GetMapping("/artifactsdetails")
  ResponseEntity<ModelAndView> artifactsdetails(@PathVariable int id) {
    ModelAndView mv = doctor_service.artifactsdetails(id);
    if (mv == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    } else {
      return new ResponseEntity<>(mv, HttpStatus.OK);
    }
  }

  @GetMapping("/getdiagonosedetail/{diagnoseid}")
  ResponseEntity<getdiagnosedetail> getdiagonosedetail(@PathVariable Integer diagnoseid) {
    getdiagnosedetail d = doctor_service.getdiagonosedetail(diagnoseid);
    if (d == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    } else {
      return new ResponseEntity<>(d, HttpStatus.OK);
    }
  }

  @PostMapping("/addprescription")
  ResponseEntity<prescriptionTable> addprescription(@RequestBody addprecriptionDTO data) {
    prescriptionTable p = doctor_service.addprescription(data);
    if (p == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    } else {
      return new ResponseEntity<>(p, HttpStatus.OK);
    }
  }

  @GetMapping("/diseasename")
  ResponseEntity<List<ICD10andnameDTO>> diseasename() {
    List<ICD10andnameDTO> s = doctor_service.diseasename();
    if (s == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    } else {
      return new ResponseEntity<>(s, HttpStatus.OK);
    }
  }

  @GetMapping("/search-patient-by-abha-id/{id}")
  ResponseEntity<PatientProfileDTO> searchpatientbyabhaid(@PathVariable String id) {
    PatientProfileDTO p = doctor_service.searchpatientbyabhaid(id);
    if (p == null) return new ResponseEntity<>(HttpStatus.OK);
    else return new ResponseEntity<>(p, HttpStatus.OK);
  }

  @GetMapping("/searchbypincode/{pincode}/{id}")
  public ResponseEntity<List<searchbydiseaseDTO>> searchbypincode(@PathVariable int pincode, @PathVariable String id) {
    List<searchbydiseaseDTO> d = doctor_service.searchbypincode(pincode, id);
    if (d != null) {
      return new ResponseEntity<>(d, HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @GetMapping("/searchbypincode/{disease}/{pincode}/{id}")
  public ResponseEntity<List<searchbydiseaseDTO>> searchbypincodeanddisease(@PathVariable String disease,@PathVariable int pincode, @PathVariable String id){
    List<searchbydiseaseDTO> l=doctor_service.searchbypincodeanddisease(disease,pincode,id);
    if(l!=null){
      return new ResponseEntity<>(l,HttpStatus.OK);
    }else
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

  @GetMapping("/download_images/{did}/{d}")
  public List<artifactsdetails> downloadArtifacts(@PathVariable String did,@PathVariable Date d){
    return doctor_service.downloadImages(did,d);
  }

  @GetMapping("/recentlytreated/{id}")
  public ResponseEntity<List<recentlytreatedDTO>> recentlytreated(@PathVariable String id){
    List<recentlytreatedDTO> l= doctor_service.recentlytreated(id);
    if(l!=null){
      return new ResponseEntity<>(l,HttpStatus.OK);
    }else
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }
  @GetMapping("/recentlytop3treated/{id}")
  public ResponseEntity<List<recentlytreatedDTO>> recentlytop3treated(@PathVariable String id){
    List<recentlytreatedDTO> l= doctor_service.recentlytop3treated(id);
    if(l!=null){
      return new ResponseEntity<>(l,HttpStatus.OK);
    }else
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

  @PostMapping("/addpatient")
  public PatientDetails addpatient(@RequestBody PatientDetails pd){
    PatientDetails p=new PatientDetails();
    p.setAddress(pd.getAddress());
    p.setAbhaid(pd.getAbhaid());
    p.setName(pd.getName());
    p.setPincode(pd.getPincode());
    p.setMobileno(pd.getMobileno());
    p.setDob(pd.getDob());
    p.setPreferredlanguage(pd.getPreferredlanguage());
    p.setGender(pd.getGender());
    p.setBlockcode(pd.getBlockcode());
    return patientDetailsRepository.save(p);
  }
}


