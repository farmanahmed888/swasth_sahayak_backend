package com.example.demo.Repository.Questionnare;

import com.example.demo.Entity.Questionnaire.Question_set;
import com.example.demo.Entity.Questionnaire.optiontabeforMCQ;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface optiontableformcqrepositary extends JpaRepository<optiontabeforMCQ,Integer> {
    List<optiontabeforMCQ> findAllByOptionid(Question_set o);
    List<optiontabeforMCQ> findByQuestionid(Question_set qid);
    List<optiontabeforMCQ> findAllByQuestionid(Question_set qid);
}
