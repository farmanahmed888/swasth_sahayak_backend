package com.example.demo.autoRunAlgorithms;

import com.example.demo.Entity.FollowUp.followUpData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
@Service
public class followupsReadjust {
    @Autowired
    private com.example.demo.Repository.Followup.followupdataRepository followupdataRepository;
    @Autowired com.example.demo.Repository.field_worker_Repositary.fieldwokerDetailRepositary fieldwokerDetailRepositary;
    public void adjust(int blockCode, int fieldworkerId) {
        LocalDate today = LocalDate.now();
        System.out.println(today);
        System.out.println(fieldworkerId);
        List<followUpData> todayFollowUp = followupdataRepository.findAllByFollowupdateAndVisited(Date.valueOf(today),false);
        for(followUpData it: todayFollowUp){
            if(it.getPatientid().getBlockcode()==blockCode && it.getFieldworkerid().getFid()!=fieldworkerId) {
                it.setFieldworkerid(fieldwokerDetailRepositary.findByFid(fieldworkerId));
                           }
        }
        followupdataRepository.saveAll(todayFollowUp);
    }
}
