package com.example.demo.Service.FieldWorker.ResponseServiceLayer;

import com.example.demo.DTO.FieldWorker.AvailableDoctorCountDTO.questionnaireMappedToPid;
import com.example.demo.DTO.FieldWorker.Follow_up_response.*;
import com.example.demo.Entity.FollowUp.VisitsToReschedule;
import com.example.demo.Entity.FollowUp.followUpData;
import com.example.demo.Entity.Questionnaire.diagnose_id_gen;
import com.example.demo.Entity.Questionnaire.patient_answers;
import com.example.demo.Entity.common_entities.patientDoctorMapping;
import com.example.demo.Entity.patient.Artifacts;
import com.example.demo.Entity.patient.PatientDetails;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.io.IOException;

import java.sql.Date;
import java.time.LocalDate;
import java.util.*;


@Service
public class FieldWorkerResponse {

    @Autowired
    private com.example.demo.Repository.Patient.patientDetailsRepository patientDetailsRepository;
    @Autowired
    private com.example.demo.Repository.Followup.followupdataRepository followupdataRepository;
    @Autowired
    private com.example.demo.Repository.Followup.visitstorescheduleRepository visitstorescheduleRepository;
    @Autowired
    private com.example.demo.Repository.CommonRepositories.patientDoctorRepository patientDoctorRepository;
    @Autowired
    private  com.example.demo.Repository.Questionnare.DiagnoseID DiagnoseID;
    @Autowired
    private com.example.demo.Repository.Questionnare.QuestionSet QuestionSet;
    @Autowired
    private com.example.demo.Repository.Questionnare.patientanswerRepositary patientanswerRepositary;
    @Autowired
    private com.example.demo.Repository.Patient.arifactsrepositary arifactsrepositary;
    @Autowired
    private com.example.demo.AmazonS3.AwsController AwsController;
    @Autowired
    private com.example.demo.autoRunAlgorithms.rescheduleFollow_ups rescheduleFollow_ups;
    @Autowired
    private com.example.demo.Service.Patient.PatientDetailsImpl patientAbhaId;


    @Autowired
    private com.example.demo.Repository.field_worker_Repositary.fieldWorkerEncrpt fieldWorkerEncrpt;

    public static List<answersDTO> jsonParse(String jsonInput) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            // Parse the JSON string as a JSON array
            List<answersDTO> answers = objectMapper.readValue(jsonInput, new TypeReference<List<answersDTO>>() {});
            return answers;
        } catch (Exception e) {
            e.printStackTrace(); // or handle the exception according to your application's requirements
            return null; // or throw an exception, or return an empty list
        }
    }


    public boolean recieveData(ResponseDTO response){
        try {

            System.out.println("\n----------------Date Received-----------------\n");
            System.out.println(response);
            if (response != null) {
                System.out.println(response.getPatient_registeration());
                if (response.getPatient_registeration() != null) {
                    registerPatient(response.getPatient_registeration());
                }
                if (response.getChosen_doctor() != null) {
                    patientDoctorMapping(response.getChosen_doctor());
                }
                rescheduleFollowUps(response);
                rescheduleFollow_ups.rescheduleMissedFollowUps();
                System.out.println("--------------SUCCESS----------------------");
                return true;
            } else {
                // Handle the case where response is null
                System.out.println("Response object is null.");
                return false;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

//Newly registered patients are added to the database through the registerPatient function, utilizing data received from the frontend.
    public boolean registerPatient(List<registeredPatientDTO> registeredPatientDTOList){
        List<PatientDetails> detailsToSave = new ArrayList<>();
        try {
            for (registeredPatientDTO temp : registeredPatientDTOList) {
                PatientDetails data = new PatientDetails();
                data.setAbhaid(temp.getPatient_abhaid());
                data.setMobileno(temp.getPatient_phoneNumber());
                data.setName(temp.getPatient_name());
                data.setBlockcode(temp.getPatient_blockCode());
                data.setGender(temp.getPatient_gender());
                java.util.Date UtilDate  = temp.getPatient_dob();
                java.sql.Date sql_date = new java.sql.Date(UtilDate.getTime());
                data.setDob(sql_date);
                data.setPincode(temp.getPatient_pincode());
                data.setAddress(temp.getPatient_address());
                data.setPreferredlanguage(temp.getPatient_preferred_language());
                detailsToSave.add(data);
            }
            patientDetailsRepository.saveAll(detailsToSave);
            return true; // If the above line executes without throwing an exception, saving was successful.
        } catch (Exception e) {
            return false; // Return false indicating that saving failed.
        }
    }

    /**
     * Reschedules Follow-Up Appointments and Updates Data
     * This method manages the rescheduling of follow-up appointments based on visit status,
     * updates related data entries, and records successful follow-ups into history.
     * @param response The response containing follow-up status and questionnaire responses
     */
    public Exception rescheduleFollowUps(ResponseDTO response ) {
        try {
            //LocalDate today = LocalDate.now();
            List<followUpStatusDTO> followUpStatusDTOList = response.getFollow_up();
            List<followUpStatusDTO> rescheduleFollowUps = new ArrayList<>();
            List<followUpStatusDTO> completedFollowUps = new ArrayList<>();

            System.out.println(response.getFollow_up());
            //followUps segregated based on visit status
            if(followUpStatusDTOList!=null){
                for (followUpStatusDTO it : followUpStatusDTOList) {
                    if (it.isVisited_status()) {
                        completedFollowUps.add(it);
                    } else {
                        rescheduleFollowUps.add(it);
                    }
                }
            }
            System.out.println(rescheduleFollowUps);
            System.out.println(completedFollowUps);
            //mapping response of ans to Pid
            List<questionnaireResponseDTO> questionnaireResponseDList = response.getQuestionnaire_response();
            List<questionnaireMappedToPid> allQuestionnaireWithPid = new ArrayList<>();
            if(questionnaireResponseDList != null){
                for (questionnaireResponseDTO temp : questionnaireResponseDList) {
                    questionnaireMappedToPid patientabhatoid = new questionnaireMappedToPid();
                    patientabhatoid.setPid(patientAbhaId.getDetails(temp.getPatient_abhaid()).getPid());
                    patientabhatoid.setQues(temp);
                    allQuestionnaireWithPid.add(patientabhatoid);
                }
            }

            //making entries in reschedule table
            List<VisitsToReschedule> RescheduleFollowupData = new ArrayList<>();
            for (followUpStatusDTO it : rescheduleFollowUps) {
                followUpData temp = followupdataRepository.findByFollowupid(it.getFollow_up_id());
                VisitsToReschedule data = new VisitsToReschedule();
                data.setMissedFollowUpDate(temp.getFollowupdate());
                data.setRescheduleStatus(false);
                data.setFollowUpId(temp);
                RescheduleFollowupData.add(data);
                temp.setVisited(false);
                followupdataRepository.save(temp);

            }
            visitstorescheduleRepository.saveAll(RescheduleFollowupData);



/**
------------------------------------------------------------------------------------------------------------------------------
If visitedStatus is true in JSON object list, all those followUps are populated in completedFollowUps.
diagnosisId is generated for each object in list. For that  diagnosisId, collected questionnaire responses will be saved.
artifacts are also uploaded to cloud using saveArtifacts() corresponding to generated diagnosisId.
Once all done, VisitedStatus is updated from False to True in followUpData table.
------------------------------------------------------------------------------------------------------------------------------
*/
            List<questionnaireMappedToPid> questionnaireResponseOfFollowUps = new ArrayList<>();
            for (followUpStatusDTO it : completedFollowUps) {
                followUpData temp = followupdataRepository.findByFollowupid(it.getFollow_up_id());
                PatientDetails patientDetails=patientAbhaId.getDetails(it.getPatient_abhaid());

                diagnose_id_gen generatedDiagnosisId  = generateDiagnosisId(it.getFollow_up_id(),it.getPatient_abhaid(),fieldWorkerEncrpt.findByFieldworkerid(response.getFieldworker_id()).getFid().getFid(),temp.getIcd10().getIcd10(),temp.getFollowupdate());
                //saveResponsesForFollowUps() will return all the questionnaireMappedToPid which are accessed. These are then added to new list i.e. questionnaireResponseOfFollowUps
                //At the end of for loop, questionnaireResponseOfFollowUps is populated with all QuestionnaireResponses which have been accessed
               List<questionnaireMappedToPid> tempList =  saveResponsesForFollowUps(patientDetails.getPid(), generatedDiagnosisId ,allQuestionnaireWithPid);
               questionnaireResponseOfFollowUps.addAll(tempList);
               //saveArtifacts(it.getPatient_abhaid(), generatedDiagnosisId.getDiagnosis_id(),response.getArtifacts());

                temp.setVisited(true);
                followupdataRepository.save(temp);
            }
//-----------------------------------------------------------------------------------------------------------------------------
            allQuestionnaireWithPid.removeAll(questionnaireResponseOfFollowUps);
//-----------------------------------------------------------------------------------------------------------------------------
            for(questionnaireMappedToPid it:allQuestionnaireWithPid){
                LocalDate today = LocalDate.now();
                PatientDetails patientDetails = patientDetailsRepository.findByPid(it.getPid());

                diagnose_id_gen diagIdObject = generateDiagnosisId( 0, patientDetails.getAbhaid(), fieldWorkerEncrpt.findByFieldworkerid(response.getFieldworker_id()).getFid().getFid(), it.getQues().getQuestionnaire_type(), Date.valueOf(today));
                //saveArtifacts(patientDetails.getAbhaid(), diagIdObject.getDiagnosis_id(),response.getArtifacts());
                diagIdObject.setFieldworkerComment(it.getQues().getComment());
                List<answersDTO> responses = jsonParse(it.getQues().getResponses());
                saveResponsesForNonFollowUps(responses,diagIdObject);
            }
//-----------------------------------------------------------------------------------------------------------------------------
            return new Exception("No exception");
        }catch (Exception e){
            System.out.println(e.getMessage());
            return e;
        }
    }
    public diagnose_id_gen generateDiagnosisId( int followUpId, String abhaId, int fieldWorkerId, String ICD10, Date Date){

            followUpData temp = followupdataRepository.findByFollowupid(followUpId);
            PatientDetails patientDetails=patientAbhaId.getDetails(abhaId);
            diagnose_id_gen diag_id = new diagnose_id_gen();
            diag_id.setFollowupid(temp);
            diag_id.setFieldworkerid(fieldWorkerId);
            diag_id.setDate(Date);
            diag_id.setPid(patientDetails.getPid());
            diag_id.setICD10(ICD10);
            diagnose_id_gen generatedDiagnosisId = DiagnoseID.save(diag_id);
            return generatedDiagnosisId;
    }

    public void saveResponsesForNonFollowUps(List<answersDTO> responses,diagnose_id_gen diagIdObject){

        for (answersDTO res : responses) {

            patient_answers ans = new patient_answers();
            ans.setQues_id(QuestionSet.findByQuestionId(res.getQuestion_id()));
            ans.setAnswer(res.getUser_response());
            ans.setDiagnoseid(diagIdObject);
            patientanswerRepositary.save(ans);
        }

    }

    //This Function will save response of questionnaire and returns a list of answer response DTO accessed.
    public List<questionnaireMappedToPid> saveResponsesForFollowUps(int patientId, diagnose_id_gen diagIdObject , List<questionnaireMappedToPid> questionnaireWithPid){
        List<questionnaireMappedToPid> dtosAccessed = new ArrayList<>();
        for (questionnaireMappedToPid ques : questionnaireWithPid) {
            if (ques.getPid() == patientId) {
                diagIdObject.setFieldworkerComment(ques.getQues().getComment());
                List<answersDTO> responses = jsonParse(ques.getQues().getResponses());
                for (answersDTO res : responses) {
                    patient_answers ans = new patient_answers();
                    ans.setQues_id(QuestionSet.findByQuestionId(res.getQuestion_id()));
                    ans.setAnswer(res.getUser_response());
                    ans.setDiagnoseid(diagIdObject);
                    patientanswerRepositary.save(ans);
                }
                dtosAccessed.add(ques);
            }
        }
        return dtosAccessed;

    }
/**
 Maps Patients to Chosen Doctors
 This method maps patients to their chosen doctors based on the provided list of chosen doctor DTOs.
 chosenDoctorList The list of chosen doctor DTOs containing patient and doctor IDs
*/
    public void patientDoctorMapping(List<choosenDoctorDTO>choosenDoctorList){

       List<patientDoctorMapping> data = new ArrayList<>();
       for(choosenDoctorDTO it: choosenDoctorList){
           patientDoctorMapping temp = new patientDoctorMapping();
           temp.setPid(patientAbhaId.getDetails(it.getPatient_abhaid()).getPid());
           temp.setDoctorid(it.getDoctor_id());
           data.add(temp);
       }
        patientDoctorRepository.saveAll(data);
    }

    /**
     * Remaining:- add artifacts to cloud and and doctor's comments as well
     *
     * @return
     */
    public BufferedImage saveArtifacts(List<artifactsDTO> artifactsDTOList) throws IOException {

        for(artifactsDTO it: artifactsDTOList){
            System.out.println(it.getAbhaId());
                Artifacts data = new Artifacts();
                List<PatientDetails> pd = patientDetailsRepository.findAll();
                for(PatientDetails p:pd){
                    if(p.getAbhaid().equals(it.getAbhaId()))
                        data.setPid(p.getPid());

                }
                data.setDate((it.getDate()));
                Artifacts ret_data =  arifactsrepositary.save(data);

                byte[] imageData;
                imageData = it.getImage().getBytes();

                String imageName = String.valueOf(ret_data.getId());
                AwsController.uploadFile(imageData,imageName);
            }

        return null;
    }



}
