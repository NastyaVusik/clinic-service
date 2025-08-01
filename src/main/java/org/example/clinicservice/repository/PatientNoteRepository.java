package org.example.clinicservice.repository;

import org.example.clinicservice.domain.PatientNote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientNoteRepository extends JpaRepository<PatientNote, Long> {
}
