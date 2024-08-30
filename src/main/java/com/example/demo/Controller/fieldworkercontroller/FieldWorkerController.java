package com.example.demo.Controller.fieldworkercontroller;

import com.example.demo.DTO.FieldWorker.AvailableDoctorCountDTO.DoctorCountDTO;
import com.example.demo.DTO.FieldWorker.Follow_up_response.*;
import com.example.demo.DTO.FieldWorker.PatientDashboard_FW_app.MainDTO;
import com.example.demo.DTO.FieldWorker.PatientDashboard_FW_app.patientDashboard_FWDTO;
import com.example.demo.DTO.FieldWorker.PatientDashboard_FW_app.PatientFollowUpDTO;
import com.example.demo.DTO.FieldWorker.QuestionnaireDTO.AllQuestionForICD10;
import com.example.demo.Entity.patient.PatientDetails;
import com.example.demo.Repository.Patient.patientDetailsRepository;
import com.example.demo.Service.FieldWorker.RequestServiceLayer.FieldWorkerRequestQuestionnaire;
import com.example.demo.Service.FieldWorker.RequestServiceLayer.FieldworkerRequest;
import com.example.demo.Service.FieldWorker.ResponseServiceLayer.FieldWorkerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping("/fieldworker")
@CrossOrigin("*")

public class FieldWorkerController {
    @Autowired
    private com.example.demo.autoRunAlgorithms.rescheduleFollow_ups rescheduleFollow_ups;
    @Autowired
    private FieldworkerRequest FieldWorkerServiceLayer;
     @Autowired
    private FieldWorkerRequestQuestionnaire QuestionnaireServiceLayer;
    @Autowired
    private FieldWorkerResponse FieldWorkerResponseServiceLayer;

    @Autowired
    private com.example.demo.Repository.Patient.patientDetailsRepository patientDetailsRepository;

    // Endpoint to get all data for a field worker
    @GetMapping("/getdata/{fieldWorkerId}")
    public ResponseEntity<MainDTO> getAllDataController(@PathVariable String fieldWorkerId) {
        System.out.print("vgghjhvhjgvhjhhvjvhjbkkbjvbkjvjbjvvjhjvjhvbvb");
        MainDTO main = FieldWorkerServiceLayer.getAllData(fieldWorkerId);
        System.out.println(main);
        if (main != null) {
            return new ResponseEntity<>(main, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping("/recievedata")
    ResponseEntity recieveDataController(@RequestBody ResponseDTO ResponseDTO ){
        boolean status =FieldWorkerResponseServiceLayer.recieveData(ResponseDTO);
        if(status){
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }
    //-----------------------------Testing API end points---------------------------------------------------------------------------------------------------------
    // Endpoint to get today's follow-ups for a field worker
    @GetMapping("/getTodaysFollowUps/{fieldWorkerId}")
    ResponseEntity<List<PatientFollowUpDTO>> getTodaysFollowUpsController(@PathVariable int fieldWorkerId) {
        return new ResponseEntity<>(FieldWorkerServiceLayer.getTodaysFollowUps(fieldWorkerId), HttpStatus.OK);
    }
    // Endpoint to get count of available doctors for a block
    @GetMapping("/getDoctorsCount/{blockcode}")
    ResponseEntity<List<DoctorCountDTO>> getDoctorsCountController(@PathVariable int blockcode) {
        List<DoctorCountDTO> count = FieldWorkerServiceLayer.getDoctorsCount(blockcode);
        if (count != null)
            return new ResponseEntity<>(count, HttpStatus.OK);
        else
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
    // Endpoint to get patient details for a specific block
    @GetMapping("/blockwisePatientDetails/{blockcode}")
    public List<patientDashboard_FWDTO> blockwisePatientDetailsController(@PathVariable int blockcode) {
        return FieldWorkerServiceLayer.blockwisePatientDetails(blockcode);
    }
    // Endpoint to get pending follow-ups for block codes assigned to other field workers
    @GetMapping("/excludedpid/{fieldWorkerId}")
    List<Integer> PendingFollowupsInBlockCodesAssignedToOtherFieldWorkerscontroller(@PathVariable int fieldWorkerId) {
        return FieldWorkerServiceLayer.PendingFollowupsInBlockCodesAssignedToOtherFieldWorkers(fieldWorkerId);
    }
    // Endpoint to get all questions
    @GetMapping("/questions")
    ResponseEntity getQuestionsController() {
        List<AllQuestionForICD10> data = QuestionnaireServiceLayer.getQuestions();
        return new ResponseEntity<>(data,HttpStatus.OK);
    }
    // Endpoint to add registered patients
    @PostMapping("/addRegisteredPatient")
    ResponseEntity addRegisteredPatientController(@RequestBody List<registeredPatientDTO> data) {
        if (FieldWorkerResponseServiceLayer.registerPatient(data))
            return new ResponseEntity<>(HttpStatus.CREATED);
        else
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    // Endpoint to reschedule follow-ups
    @PostMapping("/followupsReschedule")
    ResponseEntity rescheduleFollowUpsController(@RequestBody ResponseDTO ResponseDTO ) {
        return new ResponseEntity<>(FieldWorkerResponseServiceLayer.rescheduleFollowUps( ResponseDTO),HttpStatus.BAD_REQUEST);
    }
    @GetMapping("/reschedule")
    void reschedule(){
        rescheduleFollow_ups.rescheduleMissedFollowUps();
        return;
    }
    @PostMapping("/save")
    BufferedImage saveImage(@RequestBody List<artifactsDTO> artifactsDTOList) throws IOException {

        return FieldWorkerResponseServiceLayer.saveArtifacts(artifactsDTOList);
    }
    @PostMapping("/getdata")
    public String getdata(@RequestBody String data){
        System.out.println(data);
        return data;
    }




    @GetMapping("/getdat")
    private List<PatientDetails> getdata(){
        return patientDetailsRepository.findAll();
    }

}

