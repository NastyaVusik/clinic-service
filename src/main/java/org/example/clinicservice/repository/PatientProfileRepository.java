package org.example.clinicservice.repository;

import org.example.clinicservice.domain.PatientProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PatientProfileRepository extends JpaRepository<PatientProfile, Long> {
    
    PatientProfile getPatientProfileById(Long id);
    
    @Query("SELECT p FROM PatientProfile p WHERE p.statusId IN (200, 210, 230)")
    List<PatientProfile> findActivePatients();
    
    @Query("SELECT p FROM PatientProfile p WHERE p.oldClientGuid LIKE %:clientGuid%")
    Optional<PatientProfile> findByOldClientGuid(@Param("clientGuid") String clientGuid);
    
    @Query("SELECT p FROM PatientProfile p WHERE p.statusId IN (200, 210, 230) AND (p.oldClientGuid IS NOT NULL OR p.oldClientGuids IS NOT EMPTY)")
    List<PatientProfile> findActivePatientsWithOldClientGuids();
}
