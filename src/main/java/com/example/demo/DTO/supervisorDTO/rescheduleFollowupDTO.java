package com.example.demo.DTO.supervisorDTO;

import com.example.demo.Entity.FollowUp.missedFollowUpDetailsDTO;
import lombok.*;

import java.sql.Date;
import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class rescheduleFollowupDTO {

  private int followUpId;
  private String patientName;
  private String patientContact;
  private String missedFollowUpRemarks;
  private Date nextFollowUpDate;
  private int rescheduleid;
  private List<missedFollowUpDetailsDTO> missedFollowUpsDetails;

}
