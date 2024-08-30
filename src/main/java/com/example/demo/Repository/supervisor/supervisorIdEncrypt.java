package com.example.demo.Repository.supervisor;

import com.example.demo.Entity.supervisor.supervisorIDencrption;
import org.springframework.data.jpa.repository.JpaRepository;

public interface supervisorIdEncrypt extends JpaRepository<supervisorIDencrption,Integer> {
    public supervisorIDencrption findBySupervisorid(String s);
}
