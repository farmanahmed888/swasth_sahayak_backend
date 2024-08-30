package com.example.demo.Entity.supervisor;

import com.example.demo.Entity.fieldworker.fieldworkerDetails;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class supervisorIDencrption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int seid;

    @Column(nullable = false,unique = true)
    private String supervisorid;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "sid")
    private supervisor_details sid;
}
