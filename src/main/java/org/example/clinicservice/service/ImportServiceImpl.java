package org.example.clinicservice.service;

import lombok.RequiredArgsConstructor;
import org.example.clinicservice.repository.PatientNoteRepository;
import org.example.clinicservice.repository.PatientProfileRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ImportServiceImpl {

    private final PatientNoteRepository patientNoteRepository;
    private final PatientProfileRepository patientProfileRepository;

    private static final Set<Short> ACTIVE_STATUS = Set.of((short)200, (short)210, (short)230);

    public void importClientsNotes(long id) {
 if (isActive(id)) {
//getClients
     //save to DB
 }
    }

    private boolean isActive (long id){

        return Optional.ofNullable(patientProfileRepository.getPatientProfileById(id))
                .map(profile -> profile.getStatusId())
                .filter(ACTIVE_STATUS::contains)
                .isPresent();
    }
}



