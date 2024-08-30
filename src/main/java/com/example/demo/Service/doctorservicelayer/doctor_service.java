package com.example.demo.Service.doctorservicelayer;

import com.example.demo.DTO.Commmon.patientMetaData;
import com.example.demo.DTO.Doctor.diaganoseDTO;
import com.example.demo.DTO.Doctor.doctordetailsDTO;
import com.example.demo.DTO.Doctor.recentlytreatedDTO;
import com.example.demo.DTO.Patient.PatientProfileDTO;
import com.example.demo.DTO.Patient.artifactsdetails;
import com.example.demo.DTO.Prescription.addprecriptionDTO;
import com.example.demo.DTO.Prescription.prescriptionDTO;
import com.example.demo.DTO.Prescription.searchbydiseaseDTO;
import com.example.demo.DTO.Questionnare.ICD10andnameDTO;
import com.example.demo.DTO.Questionnare.getdiagnosedetail;
import com.example.demo.DTO.Questionnare.questionanswerDTO;
import com.example.demo.Entity.FollowUp.followUpData;
import com.example.demo.Entity.Questionnaire.diagnose_id_gen;
import com.example.demo.Entity.common_entities.ICD10_mapping;
import com.example.demo.Entity.common_entities.patientDoctorMapping;
import com.example.demo.Entity.doctor.doctorDetails;
import com.example.demo.Entity.patient.Artifacts;
import com.example.demo.Entity.patient.PatientDetails;
import com.example.demo.Entity.patient.prescriptionTable;
import com.example.demo.Entity.Questionnaire.patient_answers;
import com.example.demo.Repository.CommonRepositories.patientDoctorRepository;
import com.example.demo.Repository.Doctor.DoctorEncryptedRepository;
import com.example.demo.Repository.Doctor.DoctorRepository;
import com.example.demo.Repository.Followup.followupdataRepository;
import com.example.demo.Repository.Patient.patientDetailsRepository;
import com.example.demo.Repository.Questionnare.DiagnoseID;
import com.example.demo.Repository.Questionnare.QuestionSet;
import com.example.demo.Repository.field_worker_Repositary.fieldWorkerEncrpt;
import com.example.demo.Repository.field_worker_Repositary.fieldwokerDetailRepositary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.ModelAndView;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Service
public class doctor_service {
    @Autowired
    private patientDoctorRepository patientdoctorRepository;
    @Autowired
    private DoctorEncryptedRepository doctorEncryptedRepository;
    @Autowired
    private com.example.demo.Repository.Patient.patientDetailsRepository patientDetailsRepository;
    @Autowired
    private DoctorRepository doctorrepository;
    @Autowired
    private com.example.demo.Repository.Followup.followupdataRepository followupdataRepository;
    @Autowired
    private com.example.demo.Repository.Prescription.prescriptionRepository prescriptionRepository;
    @Autowired
    private com.example.demo.Repository.Patient.arifactsrepositary arifactsrepositary;
    @Autowired
    private com.example.demo.Repository.Questionnare.DiagnoseID DiagnoseID;
    @Autowired
    private com.example.demo.Repository.Followup.artifactsRepository artifactsRepository;
    @Autowired
    private com.example.demo.Repository.field_worker_Repositary.fieldwokerDetailRepositary fieldwokerDetailRepositary;
    @Autowired
    private com.example.demo.Repository.CommonRepositories.ICD10mapping ICD10mapping;
    @Autowired
    private com.example.demo.AmazonS3.AwsController AwsController;
    @Autowired
    private com.example.demo.Service.Patient.PatientDetailsImpl findbyAbhaId;
    @Autowired
    private com.example.demo.Repository.Questionnare.QuestionSet QuestionSet;

    public List<patientMetaData> findPatientsByCount(@PathVariable String username,@PathVariable Date d) {
        List<patientDoctorMapping> patientid = patientdoctorRepository.findTop3ByDoctorid(doctorEncryptedRepository.findByUsername(username).getDid().getDid());
        List<followUpData> f=new ArrayList<>();
        List<patientMetaData> datatosend = new ArrayList<>();
        for (patientDoctorMapping mapping : patientid) {
            patientMetaData temp = new patientMetaData();
            PatientDetails a = patientDetailsRepository.findByPid(mapping.getPid());
            if (a != null) {
                temp.setName(a.getName());
                temp.setPatientid(a.getPid());
                datatosend.add(temp);
            }
        }
        if(datatosend.size()<3){
            f=followupdataRepository.findAllByDoctoridAndFollowupdate(doctorEncryptedRepository.findByUsername(username).getDid().getDid(),d);
        }
        if(f!=null){
            for(followUpData it:f){
                patientMetaData temp = new patientMetaData();
                temp.setName(it.getPatientid().getName());
                temp.setPatientid(it.getPatientid().getPid());
                datatosend.add(temp);
                if(datatosend.size()>3)break;
            }
        }
        return datatosend;
    }

    public List<patientMetaData> findAllPatients(@PathVariable String username,@PathVariable Date d) {
        List<patientDoctorMapping> patientid = patientdoctorRepository.findAllByDoctorid(doctorEncryptedRepository.findByUsername(username).getDid().getDid());
        List<followUpData> f=followupdataRepository.findAllByDoctoridAndFollowupdate(doctorEncryptedRepository.findByUsername(username).getDid().getDid(),d);
        List<patientMetaData> datatosend = new ArrayList<>();
        for (patientDoctorMapping mapping : patientid) {
            patientMetaData temp = new patientMetaData();
            PatientDetails a = patientDetailsRepository.findByPid(mapping.getPid());
            if (a != null) {
                temp.setName(a.getName());
                temp.setAbhaid(a.getAbhaid());
                temp.setPatientid(a.getPid());
                datatosend.add(temp);
            }
        }
        for (followUpData mapping : f) {
            patientMetaData temp = new patientMetaData();
            temp.setName(mapping.getPatientid().getName());
            temp.setPatientid(mapping.getPatientid().getPid());
            temp.setAbhaid(mapping.getPatientid().getAbhaid());
            datatosend.add(temp);
        }
        return datatosend;
    }

    public long findCount(@PathVariable String did) {
        return patientdoctorRepository.countByDoctorid(doctorEncryptedRepository.findByUsername(did).getDid().getDid());
    }

    public long findoldcount(@PathVariable String did, @PathVariable Date d) {
        return followupdataRepository.countByDoctoridAndAndFollowupdate(doctorEncryptedRepository.findByUsername(did).getDid().getDid(), d);
    }

    public doctordetailsDTO getDetails(@PathVariable String id) {
        doctordetailsDTO doctordetailsdto = new doctordetailsDTO();
        doctorDetails did = doctorEncryptedRepository.findByUsername(id).getDid();
        doctorDetails d = doctorrepository.findById(did.getDid()).orElse(null);
        if (d != null) {
            doctordetailsdto.setDoctorId(id);
            doctordetailsdto.setName(d.getName());
            doctordetailsdto.setBlockCode(d.getBlockcode());
            doctordetailsdto.setSpecialization(d.getSpecialization());
        } else {
            System.out.println("Null value");
        }
        return doctordetailsdto;
    }

    public PatientProfileDTO patientdashboard(@PathVariable int patientid) {
        PatientDetails patient = patientDetailsRepository.findById(patientid).orElse(null);
        PatientProfileDTO patientProfile = new PatientProfileDTO();
        List<prescriptionDTO> prescription = new ArrayList<>();
        List<diaganoseDTO> data = new ArrayList<>();
        if (patient != null) {
            patientProfile.setName(patient.getName());
            patientProfile.setMobileno(patient.getMobileno());
            patientProfile.setAddress(patient.getAddress());
            patientProfile.setPincode(patient.getPincode());
            List<prescriptionTable> prescriptiondata = prescriptionRepository.findAllByPid(patientid);
            for (prescriptionTable mapping : prescriptiondata) {
                prescriptionDTO prescriptiondto = new prescriptionDTO();
                prescriptiondto.setPrescriptionid(mapping.getPrescriptionid());
                prescriptiondto.setDateofprescription(mapping.getPrescriptiondate());
                prescriptiondto.setFeildworker(DiagnoseID.findById(mapping.getDiagnosisid()).orElse(null).getDiagnosis_id());
                prescriptiondto.setDoctorcomment(mapping.getDoctorcomment());
                prescription.add(prescriptiondto);
            }
        }
        patientProfile.setPrescription(prescription);
        List<diagnose_id_gen> diaginoseIdPid = DiagnoseID.findAllByPid(patientid);
        List<prescriptionTable> prescriptionIdPid = prescriptionRepository.findAllByPid(patientid);
        List<Integer> diaginosePid = new ArrayList<>();
        List<Integer> prescriptionPid = new ArrayList<>();
        for (prescriptionTable it : prescriptionIdPid) {
            prescriptionPid.add(it.getDiagnosisid());
        }
        for (diagnose_id_gen it : diaginoseIdPid) {
            diaginosePid.add(it.getDiagnosis_id());
        }
        diaginosePid.removeAll(prescriptionPid);
        System.out.println(diaginosePid);
        if(!diaginosePid.isEmpty()){
            for (Integer it : diaginosePid) {
                diaganoseDTO temp = new diaganoseDTO();
                temp.setDate(DiagnoseID.findById(it).get().getDate());
                temp.setDiagnoseid(it);
                temp.setFieldworkername(fieldwokerDetailRepositary.findById(DiagnoseID.findById(it).orElse(null).getFieldworkerid()).orElse(null).getName());
                data.add(temp);
            }
        }
        patientProfile.setDid(data);
        return patientProfile;
    }

    public List<searchbydiseaseDTO> searchbydisease(@PathVariable String diseasename, @PathVariable String id) {
        List<searchbydiseaseDTO> data = new ArrayList<>();
        List<prescriptionTable> searchbydisease = prescriptionRepository.findAllByDiseasename(diseasename);
        for (prescriptionTable mapping : searchbydisease) {
            searchbydiseaseDTO temp = new searchbydiseaseDTO();
            PatientDetails details = patientDetailsRepository.findById(mapping.getPid()).orElse(null);
            if (details != null && mapping.getDoctorid().equals(id)) {
                temp.setPatientname(details.getName());
                temp.setAbhaid(details.getAbhaid());
                temp.setDiseasename(diseasename);
                temp.setPrescriptiondate(mapping.getPrescriptiondate());
            }
            data.add(temp);
        }
        return data;
    }

    public ModelAndView artifactsdetails(@PathVariable int id) {

        ModelAndView mv = new ModelAndView("index");
        List<Artifacts> imageList = arifactsrepositary.findAll();
        mv.addObject("imageList", imageList);
        return mv;
    }

    public getdiagnosedetail getdiagonosedetail(@PathVariable Integer diagnoseid) {
        diagnose_id_gen q = DiagnoseID.findById(diagnoseid).orElse(null);
        getdiagnosedetail data = new getdiagnosedetail();
        System.out.println("ebhbd"+q.getDiagid().size());
        if (q != null) {
            List<patient_answers> pl=q.getDiagid();
            List<questionanswerDTO>nq=new ArrayList<>();
            if(pl!=null){
                for(patient_answers it:pl){
                    questionanswerDTO t=new questionanswerDTO();
                    t.setQuestion(QuestionSet.findByQuestionId(it.getQues_id().getQuestionId()).getQuesText());
                    t.setAnswer(it.getAnswer());
                    nq.add(t);
                }
            }
            data.setPatientanswers(nq);
            data.setDiagnoseid(diagnoseid);

            List<followUpData> f = followupdataRepository.findAllByPatientidAndFieldworkeridAndDiagnoseIdGenIsNull(patientDetailsRepository.findByPid(q.getPid()), fieldwokerDetailRepositary.findByFid(q.getFieldworkerid()));
            if (f.isEmpty()) {
                data.setDate(null);
                data.setIcd10(null);
            } else {
                data.setDate(f.getFirst().getFollowupdate());
                data.setIcd10(f.getFirst().getIcd10().getDisease());
            }
            data.setFieldworkercomment(q.getFieldworkerComment());
            data.setPid(q.getPid());
            data.setDiagonsedate(q.getDate());

        }
        return data;
    }

    public prescriptionTable addprescription(@RequestBody addprecriptionDTO data) {
        prescriptionTable p = new prescriptionTable();
        List<followUpData> s = new ArrayList<>();
        p.setPrescription(data.getPrescription());
        p.setDiseasename(data.getDiseasename());
        p.setDoctorcomment(data.getDoctorcomment());
        p.setPid(data.getPid());
        p.setPrescriptiondate(data.getPrescriptiondate());
        p.setDiagnosisid(data.getDiagnosisid());
        p.setDoctorid(data.getDoctorid());

        List<Date> da = data.getFollowUpDate();
        if (!da.isEmpty()) {
            for (Date d : da) {
                followUpData temp = new followUpData();
                temp.setFollowupdate(d);
                temp.setIcd10(ICD10mapping.findById(data.getDiseasename()).orElse(null));
                temp.setDiagnoseIdGen(null);
                temp.setVisited(false);
                temp.setDoctorid(doctorEncryptedRepository.findByUsername(data.getDoctorid()).getDid().getDid());
                temp.setPatientid(patientDetailsRepository.findByPid(data.getPid()));
                temp.setFieldworkerid(fieldwokerDetailRepositary.findByFid(DiagnoseID.findById(data.getDiagnosisid()).get().getFieldworkerid()));
                s.add(temp);
            }
            followupdataRepository.saveAll(s);

//      diagnose_id_gen q = DiagnoseID.findById(data.getDiagnosisid()).orElse(null);
//      if(q!=null){
//        q.setFollow_up_id(new followUpData());
//      }
        } else {
            diagnose_id_gen q = DiagnoseID.findById(data.getDiagnosisid()).orElse(null);
            List<followUpData> fol = new ArrayList<>();
            if (q != null) {
                if (q.getFollowupid() != null)
                    fol.addAll(followupdataRepository.findAllByPatientidAndFieldworkeridAndDiagnoseIdGenIsNull(patientDetailsRepository.findByPid(q.getPid()), fieldwokerDetailRepositary.findByFid(q.getFieldworkerid())));
                if (!fol.isEmpty()) {
                    fol.getFirst().setIcd10(ICD10mapping.findById(data.getDiseasename()).orElse(null));
                    followupdataRepository.save(fol.getFirst());
                }
            }
        }
        patientDoctorMapping pd=patientdoctorRepository.findByPid(data.getPid());
        if(pd!=null)patientdoctorRepository.delete(patientdoctorRepository.findByPid(data.getPid()));
        return prescriptionRepository.save(p);


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

    public PatientProfileDTO searchpatientbyabhaid(@PathVariable String id) {
        PatientProfileDTO data = new PatientProfileDTO();
        PatientDetails p= findbyAbhaId.getDetails(id);
        List<diagnose_id_gen> diagonse = DiagnoseID.findAllByPid(p.getPid());
        List<prescriptionTable> prescription = prescriptionRepository.findAllByPid(p.getPid());
        List<Integer> diagonseid = new ArrayList<>();
        List<Integer> prescriptionid = new ArrayList<>();

        for (diagnose_id_gen it : diagonse) {
            diagonseid.add(it.getDiagnosis_id());
        }

        for (prescriptionTable it : prescription) {
            prescriptionid.add(it.getDiagnosisid());
        }
        diagonseid.removeAll(prescriptionid);
        if (diagonseid.isEmpty()) {
            return data;
        } else {
            return patientdashboard(p.getPid());
        }
    }

    public List<searchbydiseaseDTO> searchbypincode(@PathVariable int pincode, @PathVariable String id) {
        List<searchbydiseaseDTO> data = new ArrayList<>();
        List<prescriptionTable> search = prescriptionRepository.findAllByDoctorid(id);
        for(prescriptionTable it:search){
            searchbydiseaseDTO tmp=new searchbydiseaseDTO();
            PatientDetails p=patientDetailsRepository.findById(it.getPid()).orElse(null);
            if(p!=null && p.getPincode()==pincode){
                tmp.setDiseasename(it.getDiseasename());
                tmp.setAbhaid(p.getAbhaid());
                tmp.setPatientname(p.getName());
                tmp.setPrescriptiondate(it.getPrescriptiondate());
                data.add(tmp);
            }
        }
        return data;
    }

    public List<searchbydiseaseDTO> searchbypincodeanddisease(@PathVariable String disease,@PathVariable int pincode, @PathVariable String id) {
        List<searchbydiseaseDTO> data = new ArrayList<>();
        List<prescriptionTable> search = prescriptionRepository.findAllByDoctorid(id);
        for(prescriptionTable it:search){
            searchbydiseaseDTO tmp=new searchbydiseaseDTO();
            PatientDetails p=patientDetailsRepository.findById(it.getPid()).orElse(null);
            if(p!=null && p.getPincode()==pincode && it.getDiseasename().equals(disease)){
                tmp.setDiseasename(it.getDiseasename());
                tmp.setAbhaid(p.getAbhaid());
                tmp.setPatientname(p.getName());
                tmp.setPrescriptiondate(it.getPrescriptiondate());
                data.add(tmp);
            }
        }
        return data;
    }

    public List<artifactsdetails> downloadImages(String did,Date d){
        PatientDetails p= findbyAbhaId.getDetails(did);
                List<Artifacts> artifactsList = artifactsRepository.findAllByPidAndDate(p.getPid(),d);
        List<artifactsdetails> images= new ArrayList<>();
      for(Artifacts it:artifactsList ) {
          artifactsdetails t =new artifactsdetails();
          t.setImage(AwsController.downloadFile(String.valueOf(it.getId())));
          images.add(t);
      }
      return images;
    }

    public List<recentlytreatedDTO> recentlytreated(@PathVariable String id){
        List<recentlytreatedDTO> lr=new ArrayList<>();
        List<prescriptionTable>pl=prescriptionRepository.findAllByDoctoridOrderByPrescriptionidDesc(id);
        for(prescriptionTable it:pl){
            recentlytreatedDTO t=new recentlytreatedDTO();
            t.setDisease(it.getDiseasename());
            t.setFollowupdate(DiagnoseID.findById(it.getDiagnosisid()).get().getDate());
            t.setPatientname(patientDetailsRepository.findByPid(it.getPid()).getName());
            t.setPrescriptionid(it.getPrescriptionid());
            lr.add(t);
        }
        return lr;
    }
    public List<recentlytreatedDTO> recentlytop3treated(@PathVariable String id){
        List<recentlytreatedDTO> lr=new ArrayList<>();
        List<prescriptionTable>pl=prescriptionRepository.findTop3ByDoctoridOrderByPrescriptionidDesc(id);
        for(prescriptionTable it:pl){
            recentlytreatedDTO t=new recentlytreatedDTO();
            t.setDisease(it.getDiseasename());
            t.setFollowupdate(DiagnoseID.findById(it.getDiagnosisid()).get().getDate());
            t.setPatientname(patientDetailsRepository.findByPid(it.getPid()).getName());
            t.setPrescriptionid(it.getPrescriptionid());
            lr.add(t);
        }
        return lr;
    }

}
