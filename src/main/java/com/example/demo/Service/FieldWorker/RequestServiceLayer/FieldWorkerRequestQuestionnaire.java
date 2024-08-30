package com.example.demo.Service.FieldWorker.RequestServiceLayer;

import com.example.demo.DTO.FieldWorker.QuestionnaireDTO.AllQuestionForICD10;
import com.example.demo.DTO.FieldWorker.QuestionnaireDTO.particularQuestion;
import com.example.demo.Entity.Questionnaire.Question_set;
import com.example.demo.Entity.Questionnaire.optiontabeforMCQ;
import com.example.demo.Entity.common_entities.ICD10_mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;

@Service
public class FieldWorkerRequestQuestionnaire {
    @Autowired
    private com.example.demo.Repository.CommonRepositories.ICD10mapping ICD10mapping;
    @Autowired
    private com.example.demo.Repository.Questionnare.QuestionSet QuestionSet;
    @Autowired
    private com.example.demo.Repository.Questionnare.optiontableformcqrepositary optiontableformcqrepositary;
    public List<AllQuestionForICD10> getQuestions(){
        List<ICD10_mapping> codes=ICD10mapping.findAll();
        List<AllQuestionForICD10> AllQues= new ArrayList<>();
        for(ICD10_mapping it: codes){
            String code = it.getIcd10();
            ICD10_mapping icd10 = ICD10mapping.findById(code).orElse(null);
            if(icd10==null){
                return null;
            }
            AllQuestionForICD10 temp = new AllQuestionForICD10();
            List<particularQuestion>data = new ArrayList<>();
            List<Question_set>questions=QuestionSet.findByIcd10(icd10);
            for(Question_set it2:questions){
                if(it2.getStatus()==0){
                    particularQuestion ques = new particularQuestion();
                    ques.setQuestion_id(it2.getQuestionId());
                    ques.setQues_type(it2.getType());
                    ques.setQuestion_text(it2.getQuesText());
                    String type=it2.getType();
                    if(type.equals("mcq")){
                        List<String>options = new ArrayList<>();
                        List<optiontabeforMCQ> op= optiontableformcqrepositary.findByQuestionid(it2);
                        for(optiontabeforMCQ it3: op){
                            options.add(it3.getOptions());
                        }
                       ques.setOption(options);
                    }
                    else {
                        ques.setOption(null);
                    }
                    //AllQuestionForICD10 data = new AllQuestionForICD10();
                    data.add(ques);
                }
            }

            temp.setIcd10(code);
            temp.setQuestions(data);
            AllQues.add(temp);
        }

        return AllQues;
    }

}
