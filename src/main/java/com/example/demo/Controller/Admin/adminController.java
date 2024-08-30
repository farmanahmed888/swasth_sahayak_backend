package com.example.demo.Controller.Admin;

import com.example.demo.DTO.Doctor.AlldoctorDetails;
import com.example.demo.DTO.FieldWorker.fieldWorkerDeatialDTO;
import com.example.demo.DTO.Questionnare.ICD10andnameDTO;
import com.example.demo.DTO.Questionnare.questionaddDTO;
import com.example.demo.DTO.Questionnare.questionetailsDTO;
import com.example.demo.DTO.supervisorDTO.allsupervisordetail;
import com.example.demo.Entity.Questionnaire.Question_set;
import com.example.demo.Entity.Questionnaire.optiontabeforMCQ;
import com.example.demo.Entity.common_entities.ICD10_mapping;
import com.example.demo.Entity.doctor.DoctorEncryptId;
import com.example.demo.Entity.doctor.doctorDetails;
import com.example.demo.Entity.fieldworker.fieldworkerDetails;
import com.example.demo.Entity.supervisor.supervisorIDencrption;
import com.example.demo.Entity.supervisor.supervisor_details;
import com.example.demo.Repository.Doctor.DoctorEncryptedRepository;
import com.example.demo.Repository.Doctor.DoctorRepository;
import com.example.demo.Repository.Questionnare.QuestionSet;
import com.example.demo.Service.AdminServiceLayer.AdminServiceLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.demo.Repository.field_worker_Repositary.fieldWorkerEncrpt;
import com.example.demo.Entity.fieldworker.fieldWorkerIdEncrypt;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/admin")
@CrossOrigin("*")
public class adminController {

    @Autowired

    private com.example.demo.Service.AdminServiceLayer.AdminServiceLayer AdminServiceLayer;
    @GetMapping("/allDoctorDetail")
    ResponseEntity<List<AlldoctorDetails>> DoctoreDetail() {
        List<AlldoctorDetails> msq = AdminServiceLayer.DoctoreDetail();
        if (msq != null)
            return new ResponseEntity<>(msq, HttpStatus.OK);
        else
            return new ResponseEntity<> (msq, HttpStatus.NOT_FOUND);

    }

    @PutMapping("/updatedoctordetails/{id}")
    ResponseEntity<doctorDetails> updatedoctordetails(@RequestBody AlldoctorDetails detail, @PathVariable String id) {
        doctorDetails ds = AdminServiceLayer.updatedoctordetails(detail, id);
        if (ds != null) {
            return new ResponseEntity<>(ds, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(ds, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/doctordelete/{id}/{s}")
    ResponseEntity<Void> deletedoctor(@PathVariable String id,@PathVariable int s) {
        AdminServiceLayer.deletedoctor(id,s);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/adddoctor")
    ResponseEntity<DoctorEncryptId> adddoctor(@RequestBody DoctorEncryptId doctor) {

        DoctorEncryptId ds = AdminServiceLayer.adddoctor(doctor);
        if (ds != null) {
            return new ResponseEntity<>(ds, HttpStatus.OK);
        } else
            return new ResponseEntity<>(ds, HttpStatus.NOT_FOUND);

    }

    @GetMapping("/allfieldworkerDetail")
    ResponseEntity<List<fieldWorkerDeatialDTO>> fieldWorkerDeatialDTOList() {
        List<fieldWorkerDeatialDTO> ms = AdminServiceLayer.fieldWorkerDeatialDTOList();
        if (ms != null)
            return new ResponseEntity<>(ms, HttpStatus.OK);
        else
            return new ResponseEntity<>(ms, HttpStatus.NOT_FOUND);

    }

    @PutMapping("/updatefieldworkerdetails/{id}")
    ResponseEntity<fieldWorkerDeatialDTO> update(@RequestBody fieldWorkerDeatialDTO details, @PathVariable String id) {
        fieldWorkerDeatialDTO ds = AdminServiceLayer.update(details, id);
        if (ds != null)
            return new ResponseEntity<>(ds, HttpStatus.OK);
        else
            return new ResponseEntity<>(ds, HttpStatus.NOT_FOUND);

    }

    @DeleteMapping("/fieldworkerdelete/{id}/{s}")
    ResponseEntity<Void> deletefieldworker(@PathVariable String id,@PathVariable int s) {
        AdminServiceLayer.deletefieldworker(id,s);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/addfieldworker")
    ResponseEntity<fieldWorkerIdEncrypt> addfieldworker(@RequestBody fieldWorkerIdEncrypt fieldworker) {
        fieldWorkerIdEncrypt fd = AdminServiceLayer.addfieldworker(fieldworker);

        if (fd != null) {
            return new ResponseEntity<>(fd, HttpStatus.OK);
        } else
            return new ResponseEntity<>(fd, HttpStatus.NOT_FOUND);
    }

    @GetMapping("/allquestion")
    ResponseEntity<List<questionetailsDTO>> allquestion() {
        List<questionetailsDTO> qs = AdminServiceLayer.allquestion();
        if (qs != null)
            return new ResponseEntity<>(qs, HttpStatus.OK);
        else
            return new ResponseEntity<>(qs, HttpStatus.NOT_FOUND);
    }
    @GetMapping("/question/{id}")
    ResponseEntity<questionetailsDTO> findbyquestionid(@PathVariable Integer id) {
        questionetailsDTO qs = AdminServiceLayer.findbyquestionid(id);
        if (qs != null)
            return new ResponseEntity<>(qs, HttpStatus.OK);
        else
            return new ResponseEntity<>(qs, HttpStatus.NOT_FOUND);
    }

    @PostMapping("/addquestion")
    ResponseEntity<questionaddDTO> addquestion(@RequestBody questionaddDTO question) {
        questionaddDTO dst = AdminServiceLayer.addquestion(question);
        if (dst != null)
            return new ResponseEntity<>(dst, HttpStatus.OK);
        else
            return new ResponseEntity<>(dst, HttpStatus.NOT_FOUND);

    }

    @PutMapping("/updateQuestion/{id}")
    ResponseEntity<Boolean> questionUpdate(@RequestBody questionaddDTO question, @PathVariable Integer id) {
        Boolean bs = AdminServiceLayer.questionUpdate(question, id);
        if (bs)
            return new ResponseEntity<>(bs, HttpStatus.OK);
        else
            return new ResponseEntity<>(bs, HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/deletequestion/{id}")
    ResponseEntity<Boolean> deletequestion(@PathVariable Integer id) {
        boolean bst = AdminServiceLayer.deletequestion(id);
        if (bst)
            return new ResponseEntity<>(bst, HttpStatus.OK);
        else
            return new ResponseEntity<>(bst, HttpStatus.NOT_FOUND);
    }

    @GetMapping("/allsupervisordetail")
    ResponseEntity<List<allsupervisordetail>> allsupervisordetail() {
        List<allsupervisordetail> dr = AdminServiceLayer.allsupervisordetail();
        if (dr != null)
            return new ResponseEntity<>(dr, HttpStatus.OK);
        else
            return new ResponseEntity<>(dr, HttpStatus.NOT_FOUND);
    }

    @PostMapping("/addsupervisor")
    ResponseEntity<supervisorIDencrption> addsupervisor(@RequestBody supervisorIDencrption supervisor) {
        supervisorIDencrption ds = AdminServiceLayer.addsupervisor(supervisor);
        if (ds != null) {
            return new ResponseEntity<>(ds, HttpStatus.OK);
        } else
            return new ResponseEntity<>(ds, HttpStatus.NOT_FOUND);

    }

    @PutMapping("/updatesupervisordetail/{id}")
    ResponseEntity<Boolean> updatesupervisordetail(@RequestBody allsupervisordetail update, @PathVariable String id) {
        boolean bt = AdminServiceLayer.updatesupervisordetail(update, id);
        if (bt)
            return new ResponseEntity<>(bt, HttpStatus.OK);
        else
            return new ResponseEntity<>(bt, HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/deletesupervisor/{id}")
    ResponseEntity<Void> deletesupervisor(@PathVariable String id) {
        AdminServiceLayer.deletesupervisor(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/diseasename")
    ResponseEntity<List<ICD10andnameDTO>> diseasename() {
        List<ICD10andnameDTO> s = AdminServiceLayer.diseasename();
        if (s == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(s, HttpStatus.OK);
        }
    }
}
