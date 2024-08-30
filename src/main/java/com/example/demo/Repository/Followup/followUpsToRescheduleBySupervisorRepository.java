package com.example.demo.Repository.Followup;

import com.example.demo.Entity.FollowUp.followUpsToRescheduleBySupervisor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface followUpsToRescheduleBySupervisorRepository extends JpaRepository<followUpsToRescheduleBySupervisor, Integer> {
    followUpsToRescheduleBySupervisor findByFollowupid(int i);
}
