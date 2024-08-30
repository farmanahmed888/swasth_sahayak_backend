package com.example.demo.Controller.Supervisor;

import com.example.demo.DTO.FieldWorker.PatientDashboard_FW_app.BlockcodeResponseDTO;
import com.example.demo.DTO.supervisorDTO.adjustFollowUpDateDTO;
import com.example.demo.DTO.supervisorDTO.rescheduleFollowupDTO;
import com.example.demo.DTO.supervisorDTO.supervisordetailsdto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/supervisor")
@CrossOrigin("*")
public class supervisorController {
@Autowired
    private com.example.demo.Service.SupervisorserviceLayer.supervisorServiceLayer supervisorServiceLayer;
    @GetMapping("/get-data")
    public List<rescheduleFollowupDTO> getdata(){
        return supervisorServiceLayer.rescheduleFollowUps();
}
    @PostMapping("/assign-locality/{id}")
    public void assign_locality(@RequestBody BlockcodeResponseDTO blockCode, @PathVariable String id){
        supervisorServiceLayer.assignLocality(blockCode, id);
    }
    @PutMapping("/adjust-further-follow-up-dates")
    public void adjust_further_follow_up_dates(@RequestBody adjustFollowUpDateDTO adjustFollowUpDateDTO){
        supervisorServiceLayer.adjustFurtherFollowUpDates(adjustFollowUpDateDTO);
    }
    @GetMapping("/getblockcodewithname")
    public ResponseEntity<List<BlockcodeResponseDTO>>getblockcodewithname(){
        List<BlockcodeResponseDTO> b=supervisorServiceLayer.getblockcodewithname();
        if(b!=null)return new ResponseEntity<>(b, HttpStatus.OK);
        else return new ResponseEntity<>(b, HttpStatus.NOT_FOUND);
    }
    @GetMapping("/fieldworkerdetails")
    public ResponseEntity<List<supervisordetailsdto>>fieldworkerdetails(){
        List<supervisordetailsdto> b=supervisorServiceLayer.fieldworkerdetails();
        if(b!=null)return new ResponseEntity<>(b, HttpStatus.OK);
        else return new ResponseEntity<>(b, HttpStatus.NOT_FOUND);
    }
}
