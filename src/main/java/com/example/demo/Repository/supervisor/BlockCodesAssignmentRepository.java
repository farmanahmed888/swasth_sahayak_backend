package com.example.demo.Repository.supervisor;

import com.example.demo.Entity.common_entities.AllBlockCodes;
import com.example.demo.Entity.fieldworker.fieldworkerDetails;
import com.example.demo.Entity.supervisor.BlockCodesAssignment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BlockCodesAssignmentRepository extends JpaRepository<BlockCodesAssignment,Integer> {
    public BlockCodesAssignment findByFieldworkerid(fieldworkerDetails id);
    int countAllByBlockCodes(AllBlockCodes blockcode);
    public List<BlockCodesAssignment> findAllByFieldworkerid(fieldworkerDetails id);



}
