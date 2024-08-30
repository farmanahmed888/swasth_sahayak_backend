package com.example.demo.DTO.followUpdata;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class followupdateDTO {
    private int patientfieldworkermappingid;
    private List<Integer> Question_id;
    private Boolean visited;
    private Date followupdate;
    private int doctorid;

}
