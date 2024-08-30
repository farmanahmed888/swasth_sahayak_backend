package com.example.demo.Repository.supervisor;

import com.example.demo.Entity.common_entities.AllBlockCodes;
import com.example.demo.Entity.supervisor.BlockCodesAssignment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AllblockCodesRepository extends JpaRepository<AllBlockCodes,Integer> {
    AllBlockCodes findByBlockCodes(int blockCodes);


}
