package org.example.clinicservice.repository;

import org.example.clinicservice.domain.PatientProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientProfileRepository extends JpaRepository<PatientProfile, Long> {
}
