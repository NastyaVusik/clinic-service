package org.example.clinicservice.repository;

import org.example.clinicservice.domain.PatientOldClientGuid;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientOldClientGuidRepository extends JpaRepository<PatientOldClientGuid, Long> {
}
