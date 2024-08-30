package com.example.demo.Service.FieldWorker.RequestServiceLayer;

import com.example.demo.DTO.FieldWorker.AvailableDoctorCountDTO.DoctorCountDTO;
import com.example.demo.DTO.FieldWorker.PatientDashboard_FW_app.*;
import com.example.demo.Entity.FollowUp.followUpData;
import com.example.demo.Entity.doctor.doctorDetails;
import com.example.demo.Entity.fieldworker.fieldworkerDetails;
import com.example.demo.Entity.patient.PatientDetails;
import com.example.demo.Entity.patient.prescriptionTable;
import com.example.demo.Entity.supervisor.BlockCodesAssignment;
import com.example.demo.Repository.Doctor.DoctorEncryptedRepository;
import com.example.demo.Twilio.SmsController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class FieldworkerRequest {

    @Autowired
    private com.example.demo.Repository.supervisor.AllblockCodesRepository AllblockCodesRepository;
    @Autowired
    private com.example.demo.Repository.supervisor.BlockCodesAssignmentRepository BlockCodesAssignmentRepository;
    @Autowired
    private com.example.demo.Repository.Doctor.DoctorRepository DoctorRepository;
    @Autowired
    private com.example.demo.Repository.Followup.followupdataRepository followupdataRepository;
    @Autowired
    private com.example.demo.Repository.field_worker_Repositary.fieldwokerDetailRepositary FieldwokerDetailRepositary;
    @Autowired
    private com.example.demo.Repository.Patient.patientDetailsRepository patientDetailsRepository;
    @Autowired
    private com.example.demo.Repository.Prescription.prescriptionRepository prescriptionRepository;
    @Autowired
    private FieldWorkerRequestQuestionnaire QuestionnaireServiceLayer;
@Autowired
private com.example.demo.Repository.field_worker_Repositary.fieldWorkerEncrpt fieldWorkerEncrpt;
    @Autowired
    private DoctorEncryptedRepository doctorEncryptedRepository;

    public MainDTO getAllData(String username){
        try{
        // Get block code assigned to the field worker
            int fieldWorkerId = fieldWorkerEncrpt.findByFieldworkerid(username).getFid().getFid();
            BlockCodesAssignment block = BlockCodesAssignmentRepository.findByFieldworkerid(FieldwokerDetailRepositary.findByFid(fieldWorkerId));
            int blockCode = block.getBlockCodes().getBlockCodes();
            System.out.println(blockCode);
        /** Get today's follow-ups for the field worker */
        List<PatientFollowUpDTO> followUps = getTodaysFollowUps(fieldWorkerId);

            for (PatientFollowUpDTO followUp : followUps) {
                System.out.println(followUp.getPatient_abhaid());
            }
        // Fetching server date
        LocalDate today = LocalDate.now();


        // List to store PIDs of follow-ups
        List<Integer> followUpPids = new ArrayList<>();
        for (PatientFollowUpDTO followUp : followUps) {
            followUpPids.add(followUp.getPatient_id());
        }


        // Fetch follow-up patient details -- this will return the patient dashboard in fieldworker
        List<patientDashboard_FWDTO> followUpPatientDetails =FollowupPatientDetails(followUpPids);

        // Fetch blockwise patient details
        List<patientDashboard_FWDTO> blockwisePatientDetails = new ArrayList<>(blockwisePatientDetails(blockCode));


        // List to store patient IDs for blockwise patient details
        List<Integer> blockwisePatientIds = new ArrayList<>();
        for (patientDashboard_FWDTO patient : blockwisePatientDetails) {
            blockwisePatientIds.add(patient.getPatient_id());
        }

        // Add follow-up patient details not present in blockwise details
        for (patientDashboard_FWDTO patient : followUpPatientDetails) {
            if (!blockwisePatientIds.contains(patient.getPatient_id())) {
                blockwisePatientDetails.add(patient);
            }
        }

        // Use Java streams to remove duplicates
        List<patientDashboard_FWDTO> uniqueBlockwisePatientDetails = blockwisePatientDetails.stream()
                .distinct()
                .collect(Collectors.toList());

        // List to store patient IDs excluded from follow-ups
        List<Integer> excluded = PendingFollowupsInBlockCodesAssignedToOtherFieldWorkers(fieldWorkerId);


        // Set ongoing medication orders for patients
        for (patientDashboard_FWDTO patient : uniqueBlockwisePatientDetails) {
            boolean found = false;
            for (int excludedId : excluded) {
                if (excludedId == patient.getPatient_id()) {
                    //System.out.println(excludedId);
                    // Mark patient as inaccessible and set field worker ID
                    patient.setAccessible(false);
                    int i=followupdataRepository.findByPatientidAndAndFollowupdate(patientDetailsRepository.findByPid(excludedId) , Date.valueOf(today)).getFieldworkerid().getFid();
                    patient.setFieldworker_id(i);

                    //System.out.println(i);
                    found = true;
                    break; // No need to continue searching once found
                }
            }
            // If patient is not excluded, set field worker ID based on follow-up data
            if (!found) {
                followUpData followupData = followupdataRepository.findByPatientidAndAndFollowupdate(patientDetailsRepository.findByPid(patient.getPatient_id()), Date.valueOf(today));
                if (followupData != null) {
                    patient.setFieldworker_id(followupData.getFieldworkerid().getFid());
                } else {
                    patient.setFieldworker_id(0);
                }
                patient.setAccessible(true);
            }

            int pid = patient.getPatient_id();
            followUpData follow = followupdataRepository.findByPatientidAndAndFollowupdate(patientDetailsRepository.findByPid(pid), Date.valueOf(today));
            if (follow != null) {
                System.out.println(true);
                ongoingMedicationOrdersDTO temp = new ongoingMedicationOrdersDTO();
                temp.setIcd10_code(follow.getIcd10().getIcd10());
                temp.setDate(follow.getFollowupdate());
                temp.setQuestionnaire_type(follow.getIcd10().getIcd10()); // Redundant field, setting as ICD10 again.

                temp.setDoctor_name(DoctorRepository.findByDid(follow.getDoctorid()).getName());
                patient.setOngoing_medication_orders(temp); // Setting ongoing medication orders
                prescriptionTable ongoingMedication = prescriptionRepository.findFirst1ByPidOrderByPrescriptiondateDesc(patient.getPatient_id());

                if (ongoingMedication != null) {
                    temp.setDoctor_comment(ongoingMedication.getDoctorcomment());
                }
            }
        }

        // Create MainDTO object and set required attributes
        MainDTO mainDTO = new MainDTO();
        sectorDetails sector = new sectorDetails();
        sector.setField_worker_assigned_sector(block.getBlockCodes().getBlockname());
        mainDTO.setField_worker_details(sector);
        mainDTO.setDoctors(getDoctorsCount(blockCode));
        mainDTO.setFollow_up(followUps);
        mainDTO.setPatient_details(uniqueBlockwisePatientDetails);
        mainDTO.setQuestionnaire(QuestionnaireServiceLayer.getQuestions());
            System.out.println("sent");
        return mainDTO;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }


    // Get blockwise doctor count
    public List<DoctorCountDTO> getDoctorsCount(@PathVariable int blockcode){
        try{
        List<DoctorCountDTO> data = new ArrayList<>();
        List<doctorDetails> temp = DoctorRepository.findAllByBlockcode(blockcode);
        int count = BlockCodesAssignmentRepository.countAllByBlockCodes(AllblockCodesRepository.findByBlockCodes(blockcode));
        for(doctorDetails it: temp){
            DoctorCountDTO details = new DoctorCountDTO();
            details.setDoctor_id(it.getDid());
            details.setOpen_slots(it.getCountofpatient()/count);
            details.setDoctor_name(it.getName());
            details.setBlockcode(it.getBlockcode());
            details.setWorkingAddress(it.getWorkingaddress());
            details.setSpecialization(it.getSpecialization());
            data.add(details);
        }
        return data;
        }
        catch(Exception e){
            return null;
        }
    }

    // Get follow-ups scheduled today
    public List<PatientFollowUpDTO> getTodaysFollowUps(int fieldWorkerId) {
        fieldworkerDetails fid = FieldwokerDetailRepositary.findByFid(fieldWorkerId);
        LocalDate today = LocalDate.now();
        List<Integer> followUpIds = new ArrayList<>();
        int fieldWorkerDetails = FieldwokerDetailRepositary.findByFid(fieldWorkerId).getFid();
        String fieldworkerMobile=FieldwokerDetailRepositary.findByFid(fieldWorkerId).getMobileno();
        String fieldworkerName=FieldwokerDetailRepositary.findByFid(fieldWorkerId).getName();
        if (fieldWorkerDetails == 0) {
            return new ArrayList<>();
        }
        List<followUpData> pids =  followupdataRepository.findByFieldworkeridAndAndFollowupdate(fid, Date.valueOf(today));
        if (pids==null || pids.isEmpty()) {
            return new ArrayList<>();
        }

        for (followUpData mapping : pids) {
            if(mapping.getVisited()==false) {
                followUpIds.add(mapping.getFollowupid());
            }
        }

        List<PatientFollowUpDTO> todaysFollowUpDetails = new ArrayList<>();
        for (int followUpId : followUpIds) {
            PatientFollowUpDTO patientFollowUpDTO = new PatientFollowUpDTO();
            followUpData followUpData = followupdataRepository.findByFollowupid(followUpId);
            if (followUpData != null && followUpData.getIcd10() != null ) {
                patientFollowUpDTO.setICD10(followUpData.getIcd10().getIcd10());
                patientFollowUpDTO.setFollow_up_id(followUpData.getFollowupid());
                PatientDetails PatientDetails =  patientDetailsRepository.findByPid(followUpData.getPatientid().getPid());
                if (PatientDetails!= null) {
                    patientFollowUpDTO.setPatient_id(PatientDetails.getPid());
                    patientFollowUpDTO.setPatient_name(PatientDetails.getName());
                    patientFollowUpDTO.setPatient_abhaid(PatientDetails.getAbhaid());
                    patientFollowUpDTO.setPatient_address(PatientDetails.getAddress());
                    patientFollowUpDTO.setVisited_status(followUpData.getVisited());
                    patientFollowUpDTO.setPatient_followupdate(followUpData.getFollowupdate());
                    Random random = new Random();
                    int token = random.nextInt(9999 - 1111) + 1111;
                    String strToken = Integer.toString(token);
                    //sending message
                    SmsController SmsController=new SmsController();
                    SmsController.setBody(PatientDetails.getMobileno(), PatientDetails.getName(),fieldworkerMobile,fieldworkerName,strToken);

                    patientFollowUpDTO.setPatient_token(strToken);
                }
                todaysFollowUpDetails.add(patientFollowUpDTO);
            }
        }

        return todaysFollowUpDetails;
    }

    // Blockwise patient details
    public List<patientDashboard_FWDTO> blockwisePatientDetails(int blockcode) {
        List<patientDashboard_FWDTO> patientProfiles = new ArrayList<>();
        List<PatientDetails> patients = patientDetailsRepository.findAllByBlockcode(blockcode);

        for (PatientDetails patient : patients) {
            patientDashboard_FWDTO patientProfileDTO = new patientDashboard_FWDTO();
            patientProfileDTO.setPatient_id(patient.getPid());
            patientProfileDTO.setPatient_name(patient.getName());
            patientProfileDTO.setPatient_address(patient.getAddress());
            patientProfileDTO.setPatient_abhaid(patient.getAbhaid());
            patientProfileDTO.setFieldworker_id(0);

            List<prescriptionTable> prescriptions = prescriptionRepository.findFirst3ByPidOrderByPrescriptiondateDesc(patient.getPid());
            List<top3prescriptionsDTO> data = new ArrayList<>();

            if (prescriptions != null) {
                for (prescriptionTable prescription : prescriptions) {
                    top3prescriptionsDTO top3prescriptions = new top3prescriptionsDTO();
                    top3prescriptions.setDate(prescription.getPrescriptiondate());
                    top3prescriptions.setPrescription(prescription.getPrescription());
                    top3prescriptions.setDisease_name(prescription.getDiseasename());
                    top3prescriptions.setDoctor_name(doctorEncryptedRepository.findByUsername(prescription.getDoctorid()).getDid().getName());
                    data.add(top3prescriptions);
                }
            }
            // Setting prescriptions for the patient
            patientProfileDTO.setRecent_3_prescriptions(data);
            patientProfiles.add(patientProfileDTO);
        }
        return patientProfiles;
    }

    // Follow-up patient details
    public List<patientDashboard_FWDTO> FollowupPatientDetails(List<Integer> pids){
        List<patientDashboard_FWDTO> patientProfiles = new ArrayList<>();
        List<PatientDetails> patients = new ArrayList<>();
        for(Integer it: pids){
            patients.add(patientDetailsRepository.findByPid(it));
        }

        for (PatientDetails patient : patients) {
            patientDashboard_FWDTO patientProfileDTO = new patientDashboard_FWDTO();
            patientProfileDTO.setPatient_id(patient.getPid());
            patientProfileDTO.setPatient_name(patient.getName());
            patientProfileDTO.setPatient_address(patient.getAddress());
            patientProfileDTO.setPatient_abhaid(patient.getAbhaid());
            List<prescriptionTable> prescriptions = prescriptionRepository.findFirst3ByPidOrderByPrescriptiondateDesc(patient.getPid());
            List<top3prescriptionsDTO> data = new ArrayList<>();

            if (prescriptions != null) {
                for (prescriptionTable prescription : prescriptions) {
                    top3prescriptionsDTO top3prescriptions = new top3prescriptionsDTO();
                    top3prescriptions.setDate(prescription.getPrescriptiondate());
                    top3prescriptions.setPrescription(prescription.getPrescription());
                    top3prescriptions.setDisease_name(prescription.getDiseasename());
                    top3prescriptions.setDoctor_name(doctorEncryptedRepository.findByUsername(prescription.getDoctorid()).getDid().getName());
                    data.add(top3prescriptions);
                }
            }
            // Setting prescriptions for the patient
            patientProfileDTO.setRecent_3_prescriptions(data);
            patientProfiles.add(patientProfileDTO);
        }
        return patientProfiles;

    }

    // Pending follow-ups assigned to other fieldWorkers which belongs to Block B which is assigned to FW1
    public List<Integer> PendingFollowupsInBlockCodesAssignedToOtherFieldWorkers(int fieldWorkerId) {
        try {
            fieldworkerDetails fid = FieldwokerDetailRepositary.findByFid(fieldWorkerId);
            int blockcode = BlockCodesAssignmentRepository.findByFieldworkerid(fid).getBlockCodes().getBlockCodes();

            LocalDate today = LocalDate.now();
            List<PatientDetails> patientDetailsofAllFollowups = new ArrayList<>();
            List<Integer> allFollowupsids = followupdataRepository.findAllDistinctPatientIds(Date.valueOf(today));

            List<Integer> allFollowupsPids = new ArrayList<>();
            for(Integer it :allFollowupsids){
               int i =followupdataRepository.findByFollowupid(it).getPatientid().getPid();
                allFollowupsPids.add(i);
            }
            for (Integer pid : allFollowupsPids) {
                patientDetailsofAllFollowups.add(patientDetailsRepository.findByPid(pid));
            }
            List<Integer> patientdetailsoffieldworkerblock = new ArrayList<>();
            for (PatientDetails pdetails : patientDetailsofAllFollowups) {
                if (pdetails.getBlockcode() == blockcode)
                    patientdetailsoffieldworkerblock.add(pdetails.getPid());
            }
            List<Integer> patientdetailsoffieldworkerblockassignedtofids = new ArrayList<>();
            List<followUpData> fd = followupdataRepository.findByFieldworkeridAndAndFollowupdate(fid, Date.valueOf(today));
            for (followUpData it : fd) {
                patientdetailsoffieldworkerblockassignedtofids.add(it.getPatientid().getPid());
            }
            patientdetailsoffieldworkerblock.removeAll(patientdetailsoffieldworkerblockassignedtofids);
            return patientdetailsoffieldworkerblock;
        }
        catch(Exception e){
            return null;
        }
    }
}
