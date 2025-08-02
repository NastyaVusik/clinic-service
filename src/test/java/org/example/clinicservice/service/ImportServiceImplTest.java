package org.example.clinicservice.service;

import org.example.clinicservice.client.OldSystemClient;
import org.example.clinicservice.domain.CompanyUser;
import org.example.clinicservice.domain.PatientProfile;
import org.example.clinicservice.repository.PatientNoteRepository;
import org.example.clinicservice.repository.PatientProfileRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ImportServiceImplTest {

    @Mock
    private PatientNoteRepository patientNoteRepository;
    
    @Mock
    private PatientProfileRepository patientProfileRepository;
    
    @Mock
    private OldSystemClient oldSystemClient;
    
    @Mock
    private CompanyUserServiceImpl companyUserService;
    
    @InjectMocks
    private ImportServiceImpl importService;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(importService, "dateRangeDays", 30);
    }

    @Test
    void importClientsNotes_shouldHandleEmptyPatientList() {
        // Given
        when(oldSystemClient.getAllClients()).thenReturn(Collections.emptyList());
        when(patientProfileRepository.findActivePatientsWithOldClientGuids()).thenReturn(Collections.emptyList());

        // When
        importService.importClientsNotes();

        // Then
        verify(oldSystemClient).getAllClients();
        verify(patientProfileRepository).findActivePatientsWithOldClientGuids();
        verifyNoMoreInteractions(patientNoteRepository);
    }

    @Test
    void importClientsNotes_shouldProcessActivePatients() {
        // Given
        PatientProfile patient = new PatientProfile();
        patient.setId(1L);
        patient.setStatusId((short) 200);
        patient.setOldClientGuid("test-guid-1,test-guid-2");

        when(oldSystemClient.getAllClients()).thenReturn(Collections.emptyList());
        when(patientProfileRepository.findActivePatientsWithOldClientGuids()).thenReturn(List.of(patient));

        // When
        importService.importClientsNotes();

        // Then
        verify(oldSystemClient).getAllClients();
        verify(patientProfileRepository).findActivePatientsWithOldClientGuids();
    }
}