package com.example.demo.Entity.FollowUp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class followUpsToRescheduleBySupervisor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int rescheduleid;
    private int followupid;
    private String remarks;
    private int rescheduleidofvisited;
}
