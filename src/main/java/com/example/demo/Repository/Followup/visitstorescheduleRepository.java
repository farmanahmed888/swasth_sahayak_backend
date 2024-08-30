package com.example.demo.Repository.Followup;

import com.example.demo.Entity.FollowUp.VisitsToReschedule;
import com.example.demo.Entity.FollowUp.followUpData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface visitstorescheduleRepository extends JpaRepository<VisitsToReschedule,Integer> {
    int countByFollowUpId(followUpData followUpId);
    List<VisitsToReschedule> findAllByRescheduleStatus(boolean val);
    List<VisitsToReschedule> findAllByFollowUpId (followUpData followUpId);
    VisitsToReschedule findByFollowUpId (followUpData followUpId);

}
