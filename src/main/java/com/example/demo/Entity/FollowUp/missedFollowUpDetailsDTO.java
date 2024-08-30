package com.example.demo.Entity.FollowUp;

import lombok.Data;

import java.sql.Date;

@Data
public class missedFollowUpDetailsDTO {
    private String fieldWorkerName;
    private String fieldWorkerContactNumber;
    private Date date;
}
