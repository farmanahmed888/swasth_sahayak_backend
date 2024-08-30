package com.example.demo.Repository.Questionnare;

import com.example.demo.Entity.Questionnaire.Question_set;
import com.example.demo.Entity.common_entities.ICD10_mapping;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionSet extends JpaRepository<Question_set,Integer> {
    List<Question_set> findByIcd10(ICD10_mapping code);

    Question_set findByQuestionId(int id);

    List<Question_set> findAllByStatusAndIcd10(int status,ICD10_mapping code);


}

