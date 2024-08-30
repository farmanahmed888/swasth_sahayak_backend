package com.example.demo.Entity.common_entities;

import com.example.demo.Entity.FollowUp.followUpData;
import com.example.demo.Entity.Questionnaire.Question_set;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Entity
public class ICD10_mapping {
    @Id
    private String icd10;

    @Column(nullable = false)
    private String disease;
    @OneToMany(mappedBy = "icd10")
    private List<followUpData> followupdata;
    @OneToMany(mappedBy = "icd10",cascade = CascadeType.ALL)
    private List<Question_set> questionSetMapping;
}