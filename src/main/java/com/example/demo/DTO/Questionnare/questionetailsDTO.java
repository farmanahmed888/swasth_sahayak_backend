package com.example.demo.DTO.Questionnare;

import com.example.demo.Entity.common_entities.ICD10_mapping;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class questionetailsDTO {
    private int id;
    private String ques_text;
    private String icd10;
    private String type;
    int status;
    private List<String> option;
}
