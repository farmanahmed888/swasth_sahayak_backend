package com.example.demo.Service.SupervisorserviceLayer;

import com.example.demo.DTO.FieldWorker.PatientDashboard_FW_app.BlockcodeResponseDTO;
import com.example.demo.DTO.supervisorDTO.adjustFollowUpDateDTO;
import com.example.demo.DTO.supervisorDTO.rescheduleFollowupDTO;
import com.example.demo.DTO.supervisorDTO.supervisordetailsdto;
import com.example.demo.Entity.FollowUp.VisitsToReschedule;
import com.example.demo.Entity.FollowUp.followUpData;
import com.example.demo.Entity.FollowUp.followUpsToRescheduleBySupervisor;
import com.example.demo.Entity.FollowUp.missedFollowUpDetailsDTO;
import com.example.demo.Entity.common_entities.AllBlockCodes;
import com.example.demo.Entity.fieldworker.fieldWorkerIdEncrypt;
import com.example.demo.Entity.fieldworker.fieldworkerDetails;
import com.example.demo.Entity.patient.PatientDetails;
import com.example.demo.Entity.supervisor.BlockCodesAssignment;
import com.example.demo.Repository.field_worker_Repositary.fieldWorkerEncrpt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;


@Service
public class supervisorServiceLayer {
    @Autowired
    private com.example.demo.Repository.Followup.followupdataRepository followupdataRepository;
    @Autowired
    private com.example.demo.Repository.field_worker_Repositary.fieldwokerDetailRepositary fieldwokerDetailRepositary;
    @Autowired
    private com.example.demo.Repository.supervisor.BlockCodesAssignmentRepository BlockCodesAssignmentRepository;
    @Autowired
    private com.example.demo.Repository.supervisor.AllblockCodesRepository AllblockCodesRepository;
    @Autowired
   private com.example.demo.Repository.Followup.followUpsToRescheduleBySupervisorRepository followUpsToRescheduleBySupervisorRepository;
    @Autowired
    private com.example.demo.Repository.Followup.visitstorescheduleRepository visitstorescheduleRepository;
    @Autowired
    private com.example.demo.autoRunAlgorithms.followupsReadjust followupsReadjust;
    @Autowired
    private com.example.demo.Repository.field_worker_Repositary.fieldWorkerEncrpt fieldWorkerEncrpt;

    public List<rescheduleFollowupDTO> rescheduleFollowUps(){
        List<rescheduleFollowupDTO> rescheduleFollowupDTO = new ArrayList<>();
        List<followUpsToRescheduleBySupervisor> followUps=followUpsToRescheduleBySupervisorRepository.findAll();

        for(followUpsToRescheduleBySupervisor it: followUps){
            rescheduleFollowupDTO temp = new rescheduleFollowupDTO();
            PatientDetails patient =  followupdataRepository.findByFollowupid(it.getFollowupid()).getPatientid();
            temp.setFollowUpId(it.getFollowupid());
            temp.setMissedFollowUpRemarks(it.getRemarks());
            temp.setPatientName(patient.getName());
            temp.setPatientContact(patient.getMobileno());
            temp.setRescheduleid(it.getRescheduleidofvisited());
            if(it.getRemarks().equals("Next followUp Clashed")){
                List<followUpData> nextFollowUp = followupdataRepository.findAllByPatientidAndVisitedOrderByFollowupdateAsc(patient,false);
                    followUpData next = nextFollowUp.get(1);
                    temp.setNextFollowUpDate(next.getFollowupdate());
            }
            if(it.getRemarks().equals("Three Consecutive FollowUp Miss")){
                List<VisitsToReschedule> nextFollowUp = visitstorescheduleRepository.findAllByFollowUpId(followupdataRepository.findByFollowupid(it.getFollowupid()) );
                List<missedFollowUpDetailsDTO> missedList= new ArrayList<>();
                for(VisitsToReschedule it2: nextFollowUp){

                    if(it2.getRescheduleStatus()){
                        missedFollowUpDetailsDTO temp2 = new missedFollowUpDetailsDTO();
                        temp2.setFieldWorkerName(it2.getFollowUpId().getFieldworkerid().getName());
                        temp2.setFieldWorkerContactNumber(it2.getFollowUpId().getFieldworkerid().getMobileno());
                        temp2.setDate(it2.getMissedFollowUpDate());
                        missedList.add(temp2);
                    }
                }
                temp.setMissedFollowUpsDetails(missedList);

            }
            rescheduleFollowupDTO.add(temp);
        }

        return rescheduleFollowupDTO;
    }

   public boolean assignLocality(BlockcodeResponseDTO blockcodeResponseDTO, String id) {
        int blockcode = blockcodeResponseDTO.getBlockcode();
        System.out.println(blockcode);

        fieldworkerDetails fieldworker = fieldWorkerEncrpt.findByFieldworkerid(id).getFid();
        if (fieldworker != null) {
            AllBlockCodes block = AllblockCodesRepository.findByBlockCodes(blockcode);

            if (block != null) {
                BlockCodesAssignment temp = BlockCodesAssignmentRepository.findByFieldworkerid(fieldworker);

                if (temp != null) {
                    temp.setBlockCodes(block);
                    BlockCodesAssignmentRepository.save(temp);

                    followupsReadjust.adjust(block.getBlockCodes(),temp.getFieldworkerid().getFid());
                } else {
                    BlockCodesAssignment temp2 = new BlockCodesAssignment();
                    temp2.setBlockCodes(block);
                    temp2.setFieldworkerid(fieldworker);
                    BlockCodesAssignmentRepository.save(temp2);
                    followupsReadjust.adjust(block.getBlockCodes(),fieldworker.getFid());
                }
                return true;
            } else {
                // Block not found
                return false;
            }
        } else {
            // Fieldworker not found
            return false;
        }
    }

    public void adjustFurtherFollowUpDates(adjustFollowUpDateDTO adjustFollowUpDateDTO){
        int followUpId = adjustFollowUpDateDTO.getFollowupid();
        Date newDate = adjustFollowUpDateDTO.getDate();
       PatientDetails patient  = followupdataRepository.findByFollowupid(followUpId).getPatientid();
        List<followUpData> nextFollowUp = followupdataRepository.findAllByPatientidAndVisitedOrderByFollowupdateAsc(patient,false);// Or any other date you want to use
        int ld=nextFollowUp.size();
        System.out.println(ld);
        if(ld==1){
            nextFollowUp.getFirst().setFollowupdate(newDate);
            followupdataRepository.save(nextFollowUp.getFirst());
            VisitsToReschedule vs=visitstorescheduleRepository.findById(adjustFollowUpDateDTO.getRescheduledid()).orElse(null);
            if(vs!=null) {
                vs.setRescheduleStatus(true);
                vs.setMissedFollowUpDate(newDate);
                visitstorescheduleRepository.save(vs);
            }
        }
        else{
            LocalDate followUpDate1 = nextFollowUp.get(0).getFollowupdate().toLocalDate();
            LocalDate followUpDate2 = nextFollowUp.get(1).getFollowupdate().toLocalDate();

            LocalDate date1 = newDate.toLocalDate();
            long difference = ChronoUnit.DAYS.between( followUpDate1,date1);
            System.out.println(followUpDate1);
            System.out.println(followUpDate2);
            System.out.println(difference);
            if(followUpDate1.equals(followUpDate2)){


                nextFollowUp.get(1).setFollowupdate(newDate);
                followupdataRepository.save(nextFollowUp.get(1));
                VisitsToReschedule vs=visitstorescheduleRepository.findById(adjustFollowUpDateDTO.getRescheduledid()).orElse(null);
                if(vs!=null) {
                    vs.setRescheduleStatus(true);
                    visitstorescheduleRepository.save(vs);
                }
                if(ld>3){
                    for (int i = 2; i < ld; i++) {
                        LocalDate date = nextFollowUp.get(i).getFollowupdate().toLocalDate();
                        LocalDate updated = date.plusDays(difference); // Assuming difference is of type long
                        nextFollowUp.get(i).setFollowupdate(Date.valueOf(updated));
                    }
                    followupdataRepository.saveAll(nextFollowUp);
                }
            }
            else{
                nextFollowUp.getFirst().setFollowupdate(newDate);
                followupdataRepository.save(nextFollowUp.getFirst());
                for (int i = 1; i < ld; i++) {
                    LocalDate date = nextFollowUp.get(i).getFollowupdate().toLocalDate();
                    LocalDate updated = date.plusDays(difference); // Assuming difference is of type long
                    nextFollowUp.get(i).setFollowupdate(Date.valueOf(updated));
                }
                followupdataRepository.saveAll(nextFollowUp);
                VisitsToReschedule vs=visitstorescheduleRepository.findById(adjustFollowUpDateDTO.getRescheduledid()).orElse(null);
                if(vs!=null) {
                    vs.setRescheduleStatus(true);
                    vs.setMissedFollowUpDate(newDate);
                    visitstorescheduleRepository.save(vs);
                }
            }
        }
        followUpsToRescheduleBySupervisorRepository.delete(followUpsToRescheduleBySupervisorRepository.findByFollowupid(adjustFollowUpDateDTO.getFollowupid()));
    }

    public List<BlockcodeResponseDTO> getblockcodewithname(){
        List<BlockcodeResponseDTO> b=new ArrayList<>();
        List<AllBlockCodes> a=AllblockCodesRepository.findAll();
        for(AllBlockCodes it:a){
            BlockcodeResponseDTO t= new BlockcodeResponseDTO();
            t.setBlockcode(it.getBlockCodes());
            t.setBlockname(it.getBlockname());
            b.add(t);
        }
        return b;
    }
    public List<supervisordetailsdto> fieldworkerdetails(){
        List<fieldWorkerIdEncrypt> f=fieldWorkerEncrpt.findAll();
        List<supervisordetailsdto> sd=new ArrayList<>();
        for(fieldWorkerIdEncrypt fe:f){
            supervisordetailsdto t=new supervisordetailsdto();
            t.setName(fe.getFid().getName());
            t.setMobileno(fe.getFid().getMobileno());
            t.setFieldworkerid(fe.getFieldworkerid());
            List<BlockCodesAssignment> fd=BlockCodesAssignmentRepository.findAllByFieldworkerid(fe.getFid());
            if(!fd.isEmpty())t.setBlockassign(fd.getLast().getBlockCodes().getBlockCodes());
            else t.setBlockassign(0);
            sd.add(t);
        }
        return sd;
    }
}

