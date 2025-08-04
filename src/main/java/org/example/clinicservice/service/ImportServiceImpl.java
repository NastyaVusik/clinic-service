package org.example.clinicservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.clinicservice.client.dto.ClientNotesRequestDto;
import org.example.clinicservice.client.dto.OldClientDto;
import org.example.clinicservice.client.dto.OldClientNoteDto;
import org.example.clinicservice.client.OldSystemClient;
import org.example.clinicservice.domain.CompanyUser;
import org.example.clinicservice.domain.PatientNote;
import org.example.clinicservice.domain.PatientProfile;
import org.example.clinicservice.client.dto.ClientResponseDto;
import org.example.clinicservice.exception.InvalidLoginException;
import org.example.clinicservice.mapper.ClientMapper;
import org.example.clinicservice.repository.PatientNoteRepository;
import org.example.clinicservice.repository.PatientProfileRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class ImportServiceImpl implements ImportService {

    private final PatientNoteRepository patientNoteRepository;
    private final PatientProfileRepository patientProfileRepository;
    private final OldSystemClient oldSystemClient;
    private final CompanyUserServiceImpl companyUserService;
    private final ClientMapper clientMapper;
    
    @Value("${import.notes.date-range-days:30}")
    private int dateRangeDays;
    
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    @Transactional
    public void importClientsNotes() {
        log.info("Starting notes import process");
        ImportResult result = new ImportResult();
        
        try {
            List<OldClientDto> oldClients = oldSystemClient.getAllClients();
            log.info("Retrieved {} clients from old system", oldClients.size());
            
            Map<String, String> clientAgencyMap = new HashMap<>();
            for (OldClientDto client : oldClients) {
                clientAgencyMap.put(client.getGuid(), client.getAgency());
            }
            
            List<PatientProfile> activePatients = patientProfileRepository.findActivePatientsWithOldClientGuids();
            log.info("Found {} active patients with old client GUIDs", activePatients.size());
            
            for (PatientProfile patient : activePatients) {
                try {
                    processPatientNotes(patient, clientAgencyMap, result);
                } catch (Exception e) {
                    log.error("Error processing notes for patient {}: {}", patient.getId(), e.getMessage(), e);
                    result.incrementErrorCount();
                }
            }
            
        } catch (Exception e) {
            log.error("Critical error during import process: {}", e.getMessage(), e);
            result.incrementErrorCount();
        }
        
        log.info("Import process completed. {}", result);
    }
    
    private void processPatientNotes(PatientProfile patient, Map<String, String> clientAgencyMap, ImportResult result) {
        if (patient.getOldClientGuid() == null) {
            return;
        }
        
        // Parse old client GUIDs (comma-separated)
        String[] clientGuids = patient.getOldClientGuid().split(",");
        
        for (String clientGuid : clientGuids) {
            clientGuid = clientGuid.trim();
            String agency = clientAgencyMap.get(clientGuid);
            
            if (agency == null) {
                log.warn("No agency found for client GUID: {}", clientGuid);
                continue;
            }
            
            try {
                importNotesForClient(patient, clientGuid, agency, result);
            } catch (Exception e) {
                log.error("Error importing notes for client {}: {}", clientGuid, e.getMessage(), e);
                result.incrementErrorCount();
            }
        }
    }
    
    private void importNotesForClient(PatientProfile patient, String clientGuid, String agency, ImportResult result) {
        LocalDate dateTo = LocalDate.now();
        LocalDate dateFrom = dateTo.minusDays(dateRangeDays);
        
        ClientNotesRequestDto request = ClientNotesRequestDto.builder()
                .agency(agency)
                .dateFrom(dateFrom.format(DATE_FORMATTER))
                .dateTo(dateTo.format(DATE_FORMATTER))
                .clientGuid(clientGuid)
                .build();
        
        List<OldClientNoteDto> oldNotes = oldSystemClient.getNotes(request);
        log.debug("Retrieved {} notes for client {} (patient {})", oldNotes.size(), clientGuid, patient.getId());
        
        for (OldClientNoteDto oldNote : oldNotes) {
            try {
                processNote(patient, oldNote, result);
            } catch (Exception e) {
                log.error("Error processing note {}: {}", oldNote.getGuid(), e.getMessage(), e);
                result.incrementErrorCount();
            }
        }
    }
    
    private void processNote(PatientProfile patient, OldClientNoteDto oldNote, ImportResult result) {
        Optional<PatientNote> existingNote = patientNoteRepository.findByOldNoteGuid(oldNote.getGuid());
        
        LocalDateTime createdDateTime = parseDateTime(oldNote.getCreatedDateTime());
        LocalDateTime modifiedDateTime = parseDateTime(oldNote.getModifiedDateTime());
        
        if (existingNote.isPresent()) {
            // Update existing note if old system version is newer
            PatientNote note = existingNote.get();
            if (modifiedDateTime.isAfter(note.getLastModifiedDateTime())) {
                updateNote(note, oldNote, createdDateTime, modifiedDateTime);
                patientNoteRepository.save(note);
                result.incrementUpdatedCount();
                log.debug("Updated note {} for patient {}", oldNote.getGuid(), patient.getId());
            } else {
                result.incrementSkippedCount();
                log.debug("Skipped note {} (not newer)", oldNote.getGuid());
            }
        } else {
            // Create new note
            PatientNote newNote = createNewNote(patient, oldNote, createdDateTime, modifiedDateTime);
            if (newNote != null) {
                patientNoteRepository.save(newNote);
                result.incrementCreatedCount();
                log.debug("Created new note {} for patient {}", oldNote.getGuid(), patient.getId());
            } else {
                result.incrementErrorCount();
            }
        }
    }
    
    @Override
    public List<ClientResponseDto> getAllClientsFromOldSystem() {
        log.info("Retrieving all clients from old system");
        try {
            List<OldClientDto> oldClients = oldSystemClient.getAllClients();
            log.info("Retrieved {} clients from old system", oldClients.size());
            return clientMapper.toResponseDtoList(oldClients);
        } catch (Exception e) {
            log.error("Error retrieving clients from old system: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to retrieve clients from old system", e);
        }
    }
    
    private PatientNote createNewNote(PatientProfile patient, OldClientNoteDto oldNote, 
                                     LocalDateTime createdDateTime, LocalDateTime modifiedDateTime) {
        try {
            CompanyUser user = companyUserService.getOrCreateUser(oldNote.getLoggedUser());
            if (user == null) {
                return null;
            }
            
            PatientNote note = new PatientNote();
            note.setPatient(patient);
            note.setNote(oldNote.getComments());
            note.setOldNoteGuid(oldNote.getGuid());
            note.setCreatedDateTime(createdDateTime);
            note.setLastModifiedDateTime(modifiedDateTime);
            note.setCreatedBy(user);
            note.setLastModifiedBy(user);
            
            return note;
        } catch (InvalidLoginException e) {
            log.error("Invalid login for note {}: {}", oldNote.getGuid(), e.getMessage());
            return null;
        }
    }
    
    private void updateNote(PatientNote note, OldClientNoteDto oldNote, 
                           LocalDateTime createdDateTime, LocalDateTime modifiedDateTime) {
        try {
            CompanyUser user = companyUserService.getOrCreateUser(oldNote.getLoggedUser());
            if (user != null) {
                note.setLastModifiedBy(user);
            }
        } catch (InvalidLoginException e) {
            log.warn("Invalid login for note update {}: {}", oldNote.getGuid(), e.getMessage());
        }
        
        note.setNote(oldNote.getComments());
        note.setLastModifiedDateTime(modifiedDateTime);
    }
    
    private LocalDateTime parseDateTime(String dateTimeStr) {
        if (dateTimeStr == null || dateTimeStr.trim().isEmpty()) {
            return LocalDateTime.now();
        }
        
        try {
            if (dateTimeStr.contains(" CDT") || dateTimeStr.contains(" CST")) {
                // Remove timezone for now
                String cleanDateTime = dateTimeStr.replaceAll(" [A-Z]{3}$", "");
                return LocalDateTime.parse(cleanDateTime, DATETIME_FORMATTER);
            }
            return LocalDateTime.parse(dateTimeStr, DATETIME_FORMATTER);
        } catch (DateTimeParseException e) {
            log.warn("Could not parse datetime '{}': {}", dateTimeStr, e.getMessage());
            return LocalDateTime.now();
        }
    }
    
    public static class ImportResult {
        private int createdCount = 0;
        private int updatedCount = 0;
        private int skippedCount = 0;
        private int errorCount = 0;
        
        public void incrementCreatedCount() { createdCount++; }
        public void incrementUpdatedCount() { updatedCount++; }
        public void incrementSkippedCount() { skippedCount++; }
        public void incrementErrorCount() { errorCount++; }
        
        @Override
        public String toString() {
            return String.format("ImportResult{created=%d, updated=%d, skipped=%d, errors=%d}", 
                               createdCount, updatedCount, skippedCount, errorCount);
        }
    }
}



