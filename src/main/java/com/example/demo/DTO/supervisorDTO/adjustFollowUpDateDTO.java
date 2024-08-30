package com.example.demo.DTO.supervisorDTO;

import lombok.Data;

import java.sql.Date;

@Data
public class adjustFollowUpDateDTO {
    private Date date;
    private int followupid;
    private int rescheduledid;
}
