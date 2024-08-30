package com.example.demo.Entity.common_entities;

import com.example.demo.Entity.supervisor.BlockCodesAssignment;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class AllBlockCodes {
    @Id
    private int blockCodes;
    private boolean status;
    private String blockname;
}
