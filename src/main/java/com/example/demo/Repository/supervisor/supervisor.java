package com.example.demo.Repository.supervisor;

import com.example.demo.Entity.supervisor.supervisor_details;
import org.springframework.data.jpa.repository.JpaRepository;

public interface supervisor extends JpaRepository<supervisor_details, Integer> {
}
