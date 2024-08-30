package com.example.demo.autoRunAlgorithms;

import com.example.demo.Entity.FollowUp.VisitsToReschedule;
import com.example.demo.Entity.FollowUp.followUpData;
import com.example.demo.Entity.FollowUp.followUpsToRescheduleBySupervisor;
import com.example.demo.Repository.Followup.followUpsToRescheduleBySupervisorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

//New Date is current_date + 2
//If consecutive 3 misses, notify the supervisor
//If dates clashed--> notify supervisor --> supervisor ask doctor
@Service
public class rescheduleFollow_ups {
    @Autowired
    private com.example.demo.Repository.Followup.visitstorescheduleRepository visitstorescheduleRepository;
    @Autowired
    private com.example.demo.Repository.Followup.followupdataRepository followupdataRepository;
    @Autowired
    private com.example.demo.Repository.Followup.followUpsToRescheduleBySupervisorRepository followUpsToRescheduleBySupervisorRepository;

    public void rescheduleMissedFollowUps(){
        List<VisitsToReschedule>followUpIds = visitstorescheduleRepository.findAllByRescheduleStatus(false);
        visitstorescheduleRepository.findAll();
        for(VisitsToReschedule it: followUpIds){
            int count = visitstorescheduleRepository.countByFollowUpId(it.getFollowUpId());
            followUpData followup = followupdataRepository.findByFollowupid(it.getFollowUpId().getFollowupid());
            switch(count){
                case 1: followup.setPriority("urgent");
                    break;
                case 2 : followup.setPriority("important");
                    break;
                default:
                    followUpsToRescheduleBySupervisor temp = new followUpsToRescheduleBySupervisor();
                    temp.setFollowupid(followup.getFollowupid());
                    temp.setRemarks("Three Consecutive FollowUp Miss");
                    temp.setRescheduleidofvisited(it.getRescheduleid());
                    if(followUpsToRescheduleBySupervisorRepository.findByFollowupid(followup.getFollowupid())==null){
                        followUpsToRescheduleBySupervisorRepository.save(temp);
                    }

                    continue;
            }
            LocalDate date = followup.getFollowupdate().toLocalDate();
            LocalDate updatedDate = date.plusDays(1);
            List<followUpData> nextFollowUp = followupdataRepository.findAllByPatientidAndVisitedOrderByFollowupdateAsc(followup.getPatientid(),false);
//            if(nextFollowUp.size()>1){
//                followUpData next = nextFollowUp.get(1);
//                if((next.getFollowupdate().toLocalDate()).equals(updatedDate)){
//                    followUpsToRescheduleBySupervisor temp = new followUpsToRescheduleBySupervisor();
//                    temp.setFollowupid(next.getFollowupid());
//                    temp.setRemarks("Next followUp Clashed");
//                    temp.setRescheduleidofvisited(it.getRescheduleid());
//                   if( followUpsToRescheduleBySupervisorRepository.findByFollowupid(next.getFollowupid()) ==null){
//                         followUpsToRescheduleBySupervisorRepository.save(temp);
//                   }
//                }
//            }
            followup.setFollowupdate(Date.valueOf(updatedDate));
            it.setRescheduleStatus(true);
            visitstorescheduleRepository.save(it);
            followupdataRepository.save(followup);

            /*//////////////////////////////////////////////////////////////////*/

            int ld = nextFollowUp.size();
            for (int i = 1; i < ld; i++) {
                LocalDate dat = nextFollowUp.get(i).getFollowupdate().toLocalDate();
                LocalDate updated = dat.plusDays(1); // Assuming difference is of type long
                nextFollowUp.get(i).setFollowupdate(Date.valueOf(updated));
            }
            followupdataRepository.saveAll(nextFollowUp);

        }
    }

}
