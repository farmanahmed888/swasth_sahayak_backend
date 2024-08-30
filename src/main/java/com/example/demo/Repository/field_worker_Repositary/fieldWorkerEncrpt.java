package com.example.demo.Repository.field_worker_Repositary;

import com.example.demo.Entity.fieldworker.fieldWorkerIdEncrypt;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface fieldWorkerEncrpt extends JpaRepository<fieldWorkerIdEncrypt,Integer> {
    public fieldWorkerIdEncrypt findByFieldworkerid(String s);

}
