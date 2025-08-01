package org.example.clinicservice.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "client_profile")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClientProfile {
    @Id
    @Column(name = "guid")
    private String guid;

    @Column(name = "agency")
    private String agency;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "status")
    private String status;

    @Column(name = "dob")
    private LocalDate dob;

    @Column(name = "created_date_time")
    private LocalDateTime createdDateTime;
}
