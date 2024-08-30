package com.example.demo.Service.AdminServiceLayer;

import com.example.demo.DTO.Doctor.AlldoctorDetails;
import com.example.demo.DTO.FieldWorker.fieldWorkerDeatialDTO;
import com.example.demo.DTO.Questionnare.ICD10andnameDTO;
import com.example.demo.DTO.Questionnare.questionaddDTO;
import com.example.demo.DTO.Questionnare.questionetailsDTO;
import com.example.demo.DTO.Questionnare.savemultiplequestiondto;
import com.example.demo.DTO.supervisorDTO.allsupervisordetail;
import com.example.demo.Email.EmailDetails;
import com.example.demo.Entity.JWT_entity.RefreshToken;
import com.example.demo.Entity.JWT_entity.RoleType;
import com.example.demo.Entity.JWT_entity.loginDetails;
import com.example.demo.Entity.Questionnaire.Question_set;
import com.example.demo.Entity.Questionnaire.optiontabeforMCQ;
import com.example.demo.Entity.common_entities.ICD10_mapping;
import com.example.demo.Entity.doctor.DoctorEncryptId;
import com.example.demo.Entity.doctor.doctorDetails;
import com.example.demo.Entity.fieldworker.fieldWorkerIdEncrypt;
import com.example.demo.Entity.fieldworker.fieldworkerDetails;
import com.example.demo.Entity.supervisor.supervisorIDencrption;
import com.example.demo.Entity.supervisor.supervisor_details;
import com.example.demo.Repository.Doctor.DoctorEncryptedRepository;
import com.example.demo.Repository.Doctor.DoctorRepository;
import com.example.demo.Repository.JWT_Repository.loginDetailsRepository;
import com.example.demo.Repository.Questionnare.QuestionSet;
import com.example.demo.Repository.field_worker_Repositary.fieldWorkerEncrpt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.example.demo.Email.EmailServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class AdminServiceLayer {
    @Autowired
    private DoctorEncryptedRepository doctorEncryptedRepository;
    @Autowired
    private DoctorRepository doctorrepository;
    @Autowired
    private com.example.demo.Repository.field_worker_Repositary.fieldWorkerEncrpt fieldWorkerEncrpt;
    @Autowired
    private com.example.demo.Repository.field_worker_Repositary.fieldwokerDetailRepositary fieldwokerDetailRepositary;
    @Autowired
    private com.example.demo.Repository.Questionnare.QuestionSet QuestionSet;
    @Autowired
    private com.example.demo.Repository.CommonRepositories.ICD10mapping ICD10mapping;
    @Autowired
    private com.example.demo.Repository.supervisor.supervisorIdEncrypt supervisorIdEncrypt;
    @Autowired
    private com.example.demo.Repository.supervisor.supervisor supervisor;
    @Autowired
    private com.example.demo.Repository.Questionnare.optiontableformcqrepositary optiontableformcqrepositary;

    @Autowired
    private com.example.demo.Email.EmailServiceImpl EmailServiceImpl;
    @Autowired
    private com.example.demo.Repository.JWT_Repository.loginDetailsRepository loginDetailsRepository;

    @Autowired
    private com.example.demo.Repository.JWT_Repository.RefreshTokenrep RefreshTokenrep;
    public List<AlldoctorDetails> DoctoreDetail(){
        List<DoctorEncryptId> doctor=doctorEncryptedRepository.findAll();
        List<AlldoctorDetails> data=new ArrayList<>();
        for(DoctorEncryptId it:doctor){
            AlldoctorDetails temp=new AlldoctorDetails();
            temp.setDoctorId(it.getUsername());
            temp.setName(it.getDid().getName());
            temp.setSpecialization(it.getDid().getSpecialization());
            temp.setGender(it.getDid().getGender());
            temp.setCountofpatient(it.getDid().getCountofpatient());
            temp.setMobileno(it.getDid().getMobileno());
            temp.setBlockCode(it.getDid().getBlockcode());
            temp.setDateofjoining(it.getDid().getDateofjoining());
            temp.setPinecode(it.getDid().getPincode());
            temp.setWorkingaddress(it.getDid().getWorkingaddress());
            temp.setStatus(it.getStatus());
            data.add(temp);
        }
        return data;
    }

    public doctorDetails updatedoctordetails(@RequestBody AlldoctorDetails detail, @PathVariable String id){
        doctorDetails d=doctorEncryptedRepository.findByUsername(id).getDid();
        d.setCountofpatient(detail.getCountofpatient());
        d.setDateofjoining(detail.getDateofjoining());
        d.setGender(detail.getGender());
        d.setPincode(detail.getPinecode());
        d.setWorkingaddress(detail.getWorkingaddress());
        d.setMobileno(detail.getMobileno());
        d.setName(detail.getName());
        d.setSpecialization(detail.getSpecialization());
        d.setBlockcode(detail.getBlockCode());
        doctorrepository.save(d);
        return d;
    }

    public void deletedoctor(@PathVariable String id ,@PathVariable int s){
        DoctorEncryptId f=doctorEncryptedRepository.findByUsername(id);
        if(s==1){
            RefreshTokenrep.deleteAll(RefreshTokenrep.findAllByLogins(loginDetailsRepository.findByUsername(id)));
            loginDetailsRepository.delete(loginDetailsRepository.findByUsername(id));

        }
        if(s==0){
            loginDetails l=new loginDetails();
            l.setRole(RoleType.DOCTOR);
            l.setUsername(id);

            String pass = alphaNumericString(10);
            EmailDetails details=new EmailDetails();

            details.setRecipient(doctorEncryptedRepository.findByUsername(id).getUsername());
            details.setSubject("Your Swasth Sahayak Account Credentials");
            details.setMsgBody(emailbody(doctorEncryptedRepository.findByUsername(id).getDid().getName(),doctorEncryptedRepository.findByUsername(id).getUsername(),pass));

            l.setPassword(pass);
            loginDetailsRepository.save(l);
            EmailServiceImpl.sendSimpleMail(details);
        }
        f.setStatus(s); //set 1 for inactive;
        doctorEncryptedRepository.save(f);
    }
    public DoctorEncryptId adddoctor(@RequestBody DoctorEncryptId doctor){
        List<loginDetails> ld = loginDetailsRepository.findAll();
        for(loginDetails it:ld){
            if(it.getUsername().equals(doctor.getUsername()))
                return null;;
        }
        List<DoctorEncryptId> de=doctorEncryptedRepository.findAll();
        for(DoctorEncryptId it: de){
            if(it.getDid().getMobileno().equals(doctor.getDid().getMobileno()))
                return null;
        }
        DoctorEncryptId dc=doctorEncryptedRepository.save(doctor);
        String pass = alphaNumericString(10);
        EmailDetails details=new EmailDetails();
        details.setRecipient(doctor.getUsername());
        details.setSubject("Your Swasth Sahayak Account Credentials");
        details.setMsgBody(emailbody(doctor.getDid().getName(),doctor.getUsername(),pass));
        loginDetails l=new loginDetails();
        l.setRole(RoleType.DOCTOR);
        l.setPassword(pass);
        l.setUsername(doctor.getUsername());
        loginDetailsRepository.save(l);
        EmailServiceImpl.sendSimpleMail(details);
        return dc;
    }


    public List<fieldWorkerDeatialDTO> fieldWorkerDeatialDTOList()
    {
        List<fieldWorkerIdEncrypt> data= fieldWorkerEncrpt.findAll();
        List<fieldWorkerDeatialDTO> details=new ArrayList<>();
        for(fieldWorkerIdEncrypt it:data){
            fieldWorkerDeatialDTO temp=new fieldWorkerDeatialDTO();
            temp.setFieldworkerid(it.getFieldworkerid());
            temp.setMobileno(it.getFid().getMobileno());
            temp.setGender(it.getFid().getGender());
            temp.setName(it.getFid().getName());
            temp.setStatus(it.getStatus());
            details.add(temp);
        }
        return details;
    }

    public fieldWorkerDeatialDTO update(@RequestBody fieldWorkerDeatialDTO details,@PathVariable String id){
        fieldworkerDetails f=fieldWorkerEncrpt.findByFieldworkerid(id).getFid();
        f.setName(details.getName());
        f.setMobileno(details.getMobileno());
        f.setGender(details.getGender());
        fieldwokerDetailRepositary.save(f);
        return details;
    }

    public void deletefieldworker(@PathVariable String id,@PathVariable int s){
        fieldWorkerIdEncrypt f=fieldWorkerEncrpt.findByFieldworkerid(id);
        if(s==1){
            List<RefreshToken> ld=RefreshTokenrep.findAllByLogins(loginDetailsRepository.findByUsername(id));
            RefreshTokenrep.deleteAll(ld);
            loginDetailsRepository.delete(loginDetailsRepository.findByUsername(id));

        }
        if(s==0){
            loginDetails l=new loginDetails();
            l.setRole(RoleType.FIELDWORKER);
            l.setUsername(id);

            String pass = alphaNumericString(10);
            EmailDetails details=new EmailDetails();

            details.setRecipient(id);
            details.setSubject("Your Swasth Sahayak Account Credentials");
            details.setMsgBody(emailbody(fieldWorkerEncrpt.findByFieldworkerid(id).getFid().getName(),fieldWorkerEncrpt.findByFieldworkerid(id).getFieldworkerid(),pass));

            l.setPassword(pass);
            loginDetailsRepository.save(l);
            EmailServiceImpl.sendSimpleMail(details);
        }
        f.setStatus(s); //set 1 for inactive;
        fieldWorkerEncrpt.save(f);
    }
    public fieldWorkerIdEncrypt addfieldworker(@RequestBody fieldWorkerIdEncrypt fieldworker){
        List<loginDetails> ld = loginDetailsRepository.findAll();
        for(loginDetails it:ld){
            if(it.getUsername().equals(fieldworker.getFieldworkerid()))return null;
        }
        List<fieldWorkerIdEncrypt> de=fieldWorkerEncrpt.findAll();
        for(fieldWorkerIdEncrypt it: de){
            if(it.getFid().getMobileno().equals(fieldworker.getFid().getMobileno()))
                return null;
        }
        fieldWorkerIdEncrypt fw=fieldWorkerEncrpt.save(fieldworker);
        String pass = alphaNumericString(10);
        EmailDetails details=new EmailDetails();
        details.setRecipient(fieldworker.getFieldworkerid());
        details.setSubject("Your Swasth Sahayak Account Credentials");
        details.setMsgBody(emailbody(fieldworker.getFid().getName(),fieldworker.getFieldworkerid(),pass));
        loginDetails l=new loginDetails();
        l.setRole(RoleType.FIELDWORKER);
        l.setPassword(pass);
        l.setUsername(fieldworker.getFieldworkerid());
        loginDetailsRepository.save(l);
        EmailServiceImpl.sendSimpleMail(details);
        return fw;
    }
    public List<questionetailsDTO> allquestion(){
        List<Question_set> questionSet = QuestionSet.findAll();
        List<questionetailsDTO> questionetailsDTOS=new ArrayList<>();
        for(Question_set it:questionSet){
            questionetailsDTO questionSet1=new questionetailsDTO();
            questionSet1.setId(it.getQuestionId());
            questionSet1.setIcd10(it.getIcd10().getDisease());
            questionSet1.setQues_text(it.getQuesText());
            questionSet1.setType(it.getType());
            List<optiontabeforMCQ> k =it.getOptiontabeforMCQList();
            List<String>option=new ArrayList<>();
            if(k!=null) {
                for(optiontabeforMCQ o:k){
                    option.add(o.getOptions());
                }
            }
            questionSet1.setOption(option);
            questionSet1.setStatus(it.getStatus());
            questionetailsDTOS.add(questionSet1);
        }
        return questionetailsDTOS;
    }

    public questionetailsDTO findbyquestionid(Integer id){
        Question_set q= QuestionSet.findById(id).orElse(null);
        questionetailsDTO questionSet1=new questionetailsDTO();
        if(q!=null){
            questionSet1.setId(q.getQuestionId());
            questionSet1.setIcd10(q.getIcd10().getIcd10());
            questionSet1.setQues_text(q.getQuesText());
            questionSet1.setType(q.getType());
            List<optiontabeforMCQ> k =q.getOptiontabeforMCQList();
            List<String>option=new ArrayList<>();
            if(k!=null) {
                for(optiontabeforMCQ o:k){
                    option.add(o.getOptions());
                }
            }
            questionSet1.setOption(option);
        }
        return questionSet1;
    }
    public questionaddDTO addquestion(@RequestBody questionaddDTO question){
        List<savemultiplequestiondto> qm=question.getQuestion();
        for(savemultiplequestiondto it:qm){
            Question_set t=new Question_set();
            t.setQuesText(it.getQues_text());
            ICD10_mapping m=ICD10mapping.findById(question.getIcd10()).orElse(null);
            t.setIcd10(m);
            t.setType(it.getType().toLowerCase());
            t.setStatus(0);
            if(it.getType().equals("mcq")){
                List<String> options=it.getOptions();
                for(String s:options){
                    optiontabeforMCQ mcq=new optiontabeforMCQ();
                    mcq.setOptions(s);
                    mcq.setQuestionid(t);
                    optiontableformcqrepositary.save(mcq);
                }
            }
            else{
                t.setOptiontabeforMCQList(null);
            }
            Question_set q=QuestionSet.save(t);
        }
        return question;

    }

   public boolean questionUpdate(@RequestBody questionaddDTO question ,@PathVariable Integer id){
        Question_set q= QuestionSet.findById(id).orElse(null);
       System.out.println(question.getOptions());
        if(q!=null){
            q.setQuesText(question.getQues_text());
            q.setIcd10(ICD10mapping.findById(question.getIcd10()).orElse(null));
            if(question.getType().equals("mcq")){
                List<optiontabeforMCQ> up=optiontableformcqrepositary.findAllByQuestionid(q);
                List<String>upd=question.getOptions();
                int index=0;
                for(optiontabeforMCQ om:up){
                    om.setOptions(upd.get(index));
                    index++;
                }
                optiontableformcqrepositary.saveAll(up);
            }
            QuestionSet.save(q);
            return true;
        }
        return false;

    }

    public boolean deletequestion(@PathVariable Integer id){
        Question_set q=QuestionSet.findById(id).orElse(null);
        if(q!=null) {
            q.setStatus(1^q.getStatus());
            QuestionSet.save(q);
            return true;
        }
        return false;
    }

    public List<allsupervisordetail> allsupervisordetail(){
        List<supervisorIDencrption> details=supervisorIdEncrypt.findAll();
        List<allsupervisordetail> data=new ArrayList<>();
        for(supervisorIDencrption it:details){
            allsupervisordetail temp =new allsupervisordetail();
            temp.setSupervisorid(it.getSupervisorid());
            temp.setGender(it.getSid().getGender());
            temp.setMobileno(it.getSid().getMobileno());
            temp.setName(it.getSid().getName());
            data.add(temp);
        }
        return data;
    }

    public supervisorIDencrption addsupervisor(@RequestBody supervisorIDencrption supervisor){
        List<loginDetails> ld = loginDetailsRepository.findAll();
        for(loginDetails it:ld){
            if(it.getUsername().equals(supervisor.getSupervisorid()))return null;
        }
        List<supervisorIDencrption> de=supervisorIdEncrypt.findAll();
        for(supervisorIDencrption it: de){
            if(it.getSid().getMobileno().equals(supervisor.getSid().getMobileno()))
                return null;
        }

        supervisorIDencrption s=supervisorIdEncrypt.save(supervisor);
        String pass = alphaNumericString(10);
        EmailDetails details=new EmailDetails();
        details.setRecipient(supervisor.getSupervisorid());
        details.setSubject("Your Swasth Sahayak Account Credentials");
        details.setMsgBody(emailbody(supervisor.getSid().getName(),supervisor.getSupervisorid(),pass));
        loginDetails l=new loginDetails();
        l.setRole(RoleType.SUPERVISOR);
        l.setPassword(pass);
        l.setUsername(supervisor.getSupervisorid());
        loginDetailsRepository.save(l);
        EmailServiceImpl.sendSimpleMail(details);
        return s;
    }
    public List<ICD10andnameDTO> diseasename() {
        List<ICD10_mapping> i = ICD10mapping.findAll();
        List<ICD10andnameDTO> name = new ArrayList<>();
        for (ICD10_mapping it : i) {
            ICD10andnameDTO t = new ICD10andnameDTO();
            t.setDiseasename(it.getDisease());
            t.setIcd10(it.getIcd10());
            name.add(t);
        }
        return name;
    }

    public boolean updatesupervisordetail(@RequestBody allsupervisordetail update,@PathVariable String id){
        supervisor_details d=supervisorIdEncrypt.findBySupervisorid(id).getSid();
        if(d==null)
            return false;
        else {
            d.setGender(update.getGender());
            d.setMobileno(update.getMobileno());
            d.setName(update.getName());
            supervisor.save(d);
            return true;
           }
    }
   public void deletesupervisor(@PathVariable String id){
       List<RefreshToken> ld=RefreshTokenrep.findAllByLogins(loginDetailsRepository.findByUsername(id));
       RefreshTokenrep.deleteAll(ld);
       loginDetailsRepository.delete(loginDetailsRepository.findByUsername(id));
        supervisorIdEncrypt.delete(supervisorIdEncrypt.findBySupervisorid(id));
    }

    public static String alphaNumericString(int len) {
        String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random rnd = new Random();

        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            sb.append(AB.charAt(rnd.nextInt(AB.length())));
        }
        return sb.toString();
    }
    public String emailbody(String recipientName,String username,String password){
        String emailMessage ="Dear " + recipientName + ",\n\n" +
                "We hope this email finds you well.\n\n" +
                "As requested, we are providing you with the credentials to access your Swasth Sahayak account:\n\n" +
                "Username: " + username + "\n" +
                "Password: " + password + "\n\n" +
                "Please ensure to keep these credentials confidential and do not share them with anyone else. " +
                "If you suspect any unauthorized access to your account or have any questions regarding your account, " +
                "please don't hesitate to contact us at swasth.sahayak@gmail.com.\n\n" +
                "Thank you for choosing Swasth Sahayak. We are dedicated to providing you with the best healthcare assistance and support.\n\n" +
                "Swasth Sahayak";

        System.out.println(emailMessage);
        return emailMessage;
    }

}
