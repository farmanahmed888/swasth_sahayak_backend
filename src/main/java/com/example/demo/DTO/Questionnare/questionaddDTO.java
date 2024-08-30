package com.example.demo.DTO.Questionnare;

import com.example.demo.Entity.common_entities.ICD10_mapping;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class questionaddDTO {
    private String ques_text;
    private String icd10;
    private String type;
    private List<String> options;
    private List<savemultiplequestiondto> question;
}
