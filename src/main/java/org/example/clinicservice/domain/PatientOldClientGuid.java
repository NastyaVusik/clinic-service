package org.example.clinicservice.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "patient_old_client_guid", uniqueConstraints = {
        @UniqueConstraint(columnNames = "client_guid")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PatientOldClientGuid {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "client_guid", nullable = false, columnDefinition = "uuid")
    private UUID clientGuid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    private PatientProfile patient;
}
