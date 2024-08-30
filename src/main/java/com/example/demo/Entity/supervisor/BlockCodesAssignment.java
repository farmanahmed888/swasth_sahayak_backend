package com.example.demo.Entity.supervisor;

import com.example.demo.Entity.common_entities.AllBlockCodes;
import com.example.demo.Entity.fieldworker.fieldworkerDetails;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class BlockCodesAssignment {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int id;
    @ManyToOne
    private AllBlockCodes blockCodes;
    @OneToOne
    private fieldworkerDetails fieldworkerid;
}
