package org.example.clinicservice.repository;

import org.example.clinicservice.domain.PatientNote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PatientNoteRepository extends JpaRepository<PatientNote, Long> {
    
    Optional<PatientNote> findByOldNoteGuid(String oldNoteGuid);
    
    @Query("SELECT COUNT(n) FROM PatientNote n WHERE n.oldNoteGuid IS NOT NULL")
    long countImportedNotes();
    
    boolean existsByOldNoteGuid(String oldNoteGuid);
}
