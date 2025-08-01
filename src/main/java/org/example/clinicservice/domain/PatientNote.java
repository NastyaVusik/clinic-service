package org.example.clinicservice.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "patient_note")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PatientNote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "created_date_time", nullable = false)
    private LocalDateTime createdDateTime;

    @Column(name = "last_modified_date_time", nullable = false)
    private LocalDateTime lastModifiedDateTime;

    @ManyToOne
    @JoinColumn(name = "created_by_user_id")
    private CompanyUser createdBy;

    @ManyToOne
    @JoinColumn(name = "last_modified_by_user_id")
    private CompanyUser lastModifiedBy;

    @Column(name = "note", length = 4000)
    private String note;

    @ManyToOne()
    @JoinColumn(name = "patient_id", nullable = false)
    private PatientProfile patient;
}

