package com.example.demo.Repository.field_worker_Repositary;

import com.example.demo.Entity.fieldworker.fieldworkerDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface fieldwokerDetailRepositary extends JpaRepository<fieldworkerDetails,Integer> {
    public fieldworkerDetails findByFid(int i);

}
