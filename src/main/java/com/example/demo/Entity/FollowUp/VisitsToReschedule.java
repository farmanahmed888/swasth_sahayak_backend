package com.example.demo.Entity.FollowUp;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.Null;
import lombok.*;

import java.sql.Date;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Entity
public class VisitsToReschedule {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int rescheduleid;
    @ManyToOne
    @JoinColumn(name = "followUpId")
    private followUpData followUpId;
    private Date missedFollowUpDate;
    @Column(nullable = false)
    private Boolean rescheduleStatus;


}