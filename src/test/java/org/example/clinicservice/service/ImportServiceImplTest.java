package org.example.clinicservice.service;

import org.example.clinicservice.client.OldSystemClient;
import org.example.clinicservice.client.dto.ClientNotesRequestDto;
import org.example.clinicservice.client.dto.OldClientDto;
import org.example.clinicservice.client.dto.OldClientNoteDto;
import org.example.clinicservice.domain.CompanyUser;
import org.example.clinicservice.domain.PatientNote;
import org.example.clinicservice.domain.PatientProfile;
import org.example.clinicservice.exception.InvalidLoginException;
import org.example.clinicservice.mapper.ClientMapper;
import org.example.clinicservice.repository.PatientNoteRepository;
import org.example.clinicservice.repository.PatientProfileRepository;
import org.example.clinicservice.util.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
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
    
    @Mock
    private ClientMapper clientMapper;
    
    @InjectMocks
    private ImportServiceImpl importService;

    @Test
    void importClientsNotes_shouldHandleEmptyData() {
        // Given
        ReflectionTestUtils.setField(importService, "dateRangeDays", 30);
        when(oldSystemClient.getAllClients()).thenReturn(OldClientDtoTestData.createEmptyList());
        when(patientProfileRepository.findActivePatientsWithOldClientGuids()).thenReturn(PatientProfileTestData.createEmptyList());

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
        ReflectionTestUtils.setField(importService, "dateRangeDays", 30);
        OldClientDto client = OldClientDtoTestData.createDefault();
        PatientProfile patient = PatientProfileTestData.createDefault();
        
        when(oldSystemClient.getAllClients()).thenReturn(List.of(client));
        when(patientProfileRepository.findActivePatientsWithOldClientGuids()).thenReturn(List.of(patient));
        when(oldSystemClient.getNotes(any(ClientNotesRequestDto.class))).thenReturn(OldClientNoteDtoTestData.createEmptyList());

        // When
        importService.importClientsNotes();

        // Then
        verify(oldSystemClient).getAllClients();
        verify(patientProfileRepository).findActivePatientsWithOldClientGuids();
        verify(oldSystemClient).getNotes(any(ClientNotesRequestDto.class));
    }

    @Test
    void importClientsNotes_shouldCreateNewNote() {
        // Given
        ReflectionTestUtils.setField(importService, "dateRangeDays", 30);
        OldClientDto client = OldClientDtoTestData.createDefault();
        PatientProfile patient = PatientProfileTestData.createDefault();
        OldClientNoteDto note = OldClientNoteDtoTestData.createDefault();
        CompanyUser user = CompanyUserTestData.createDefault();
        
        when(oldSystemClient.getAllClients()).thenReturn(List.of(client));
        when(patientProfileRepository.findActivePatientsWithOldClientGuids()).thenReturn(List.of(patient));
        when(oldSystemClient.getNotes(any(ClientNotesRequestDto.class))).thenReturn(List.of(note));
        when(patientNoteRepository.findByOldNoteGuid(note.getGuid())).thenReturn(Optional.empty());
        when(companyUserService.getOrCreateUser(note.getLoggedUser())).thenReturn(user);

        // When
        importService.importClientsNotes();

        // Then
        ArgumentCaptor<PatientNote> noteCaptor = ArgumentCaptor.forClass(PatientNote.class);
        verify(patientNoteRepository).save(noteCaptor.capture());
        
        PatientNote savedNote = noteCaptor.getValue();
        assertThat(savedNote.getPatient()).isEqualTo(patient);
        assertThat(savedNote.getNote()).isEqualTo(note.getComments());
        assertThat(savedNote.getOldNoteGuid()).isEqualTo(note.getGuid());
        assertThat(savedNote.getCreatedBy()).isEqualTo(user);
        assertThat(savedNote.getLastModifiedBy()).isEqualTo(user);
    }

    @Test
    void importClientsNotes_shouldUpdateExistingNote() {
        // Given
        ReflectionTestUtils.setField(importService, "dateRangeDays", 30);
        OldClientDto client = OldClientDtoTestData.createDefault();
        PatientProfile patient = PatientProfileTestData.createDefault();
        OldClientNoteDto note = OldClientNoteDtoTestData.createNewer();
        CompanyUser user = CompanyUserTestData.createDefault();
        PatientNote existingNote = PatientNoteTestData.createOlderNote();

        when(oldSystemClient.getAllClients()).thenReturn(List.of(client));
        when(patientProfileRepository.findActivePatientsWithOldClientGuids()).thenReturn(List.of(patient));
        when(oldSystemClient.getNotes(any(ClientNotesRequestDto.class))).thenReturn(List.of(note));
        when(patientNoteRepository.findByOldNoteGuid(note.getGuid())).thenReturn(Optional.of(existingNote));
        when(companyUserService.getOrCreateUser(note.getLoggedUser())).thenReturn(user);

        // When
        importService.importClientsNotes();

        // Then
        ArgumentCaptor<PatientNote> noteCaptor = ArgumentCaptor.forClass(PatientNote.class);
        verify(patientNoteRepository).save(noteCaptor.capture());
        
        PatientNote updatedNote = noteCaptor.getValue();
        assertThat(updatedNote.getNote()).isEqualTo(note.getComments());
        assertThat(updatedNote.getLastModifiedBy()).isEqualTo(user);
    }

    @Test
    void importClientsNotes_shouldSkipOlderNote() {
        // Given
        ReflectionTestUtils.setField(importService, "dateRangeDays", 30);
        OldClientDto client = OldClientDtoTestData.createDefault();
        PatientProfile patient = PatientProfileTestData.createDefault();
        OldClientNoteDto note = OldClientNoteDtoTestData.createOlder();
        PatientNote existingNote = PatientNoteTestData.createNewerNote();

        when(oldSystemClient.getAllClients()).thenReturn(List.of(client));
        when(patientProfileRepository.findActivePatientsWithOldClientGuids()).thenReturn(List.of(patient));
        when(oldSystemClient.getNotes(any(ClientNotesRequestDto.class))).thenReturn(List.of(note));
        when(patientNoteRepository.findByOldNoteGuid(note.getGuid())).thenReturn(Optional.of(existingNote));

        // When
        importService.importClientsNotes();

        // Then
        verify(patientNoteRepository, never()).save(any(PatientNote.class));
    }

    @Test
    void importClientsNotes_shouldHandleInvalidUser() {
        // Given
        ReflectionTestUtils.setField(importService, "dateRangeDays", 30);
        OldClientDto client = OldClientDtoTestData.createDefault();
        PatientProfile patient = PatientProfileTestData.createDefault();
        OldClientNoteDto note = OldClientNoteDtoTestData.createDefault();
        
        when(oldSystemClient.getAllClients()).thenReturn(List.of(client));
        when(patientProfileRepository.findActivePatientsWithOldClientGuids()).thenReturn(List.of(patient));
        when(oldSystemClient.getNotes(any(ClientNotesRequestDto.class))).thenReturn(List.of(note));
        when(patientNoteRepository.findByOldNoteGuid(note.getGuid())).thenReturn(Optional.empty());
        when(companyUserService.getOrCreateUser(note.getLoggedUser()))
                .thenThrow(new InvalidLoginException("Invalid login"));

        // When
        importService.importClientsNotes();

        // Then
        verify(patientNoteRepository, never()).save(any(PatientNote.class));
    }

    @ParameterizedTest
    @MethodSource("org.example.clinicservice.util.TestParameters#oldClientGuidParameters")
    void importClientsNotes_shouldHandleMultipleClientGuids(String oldClientGuids, String[] expectedGuids) {
        // Given
        ReflectionTestUtils.setField(importService, "dateRangeDays", 30);
        PatientProfile patient = PatientProfileTestData.createWithOldClientGuids(oldClientGuids);
        
        List<OldClientDto> clients = expectedGuids.length > 0 ? 
                List.of(OldClientDtoTestData.createWithGuid(expectedGuids[0])) : 
                OldClientDtoTestData.createEmptyList();

        when(oldSystemClient.getAllClients()).thenReturn(clients);
        when(patientProfileRepository.findActivePatientsWithOldClientGuids()).thenReturn(List.of(patient));
        when(oldSystemClient.getNotes(any(ClientNotesRequestDto.class))).thenReturn(OldClientNoteDtoTestData.createEmptyList());

        // When
        importService.importClientsNotes();

        // Then
        verify(oldSystemClient, times(expectedGuids.length > 0 ? 1 : 0)).getNotes(any(ClientNotesRequestDto.class));
    }

    @Test
    void importClientsNotes_shouldHandleMissingAgency() {
        // Given
        ReflectionTestUtils.setField(importService, "dateRangeDays", 30);
        PatientProfile patient = PatientProfileTestData.createWithOldClientGuids("missing-guid");
        OldClientDto client = OldClientDtoTestData.createDefault();
        
        when(oldSystemClient.getAllClients()).thenReturn(List.of(client));
        when(patientProfileRepository.findActivePatientsWithOldClientGuids()).thenReturn(List.of(patient));

        // When
        importService.importClientsNotes();

        // Then
        verify(oldSystemClient, never()).getNotes(any(ClientNotesRequestDto.class));
    }

    @Test
    void getAllClientsFromOldSystem_shouldReturnMappedClients() {
        // Given
        List<OldClientDto> clients = OldClientDtoTestData.createMultipleClients();
        when(oldSystemClient.getAllClients()).thenReturn(clients);
        when(clientMapper.toResponseDtoList(clients)).thenReturn(Collections.emptyList());

        // When
        importService.getAllClientsFromOldSystem();

        // Then
        verify(oldSystemClient).getAllClients();
        verify(clientMapper).toResponseDtoList(clients);
    }

    @Test
    void getAllClientsFromOldSystem_shouldHandleException() {
        // Given
        when(oldSystemClient.getAllClients()).thenThrow(new RuntimeException("Connection error"));

        // When & Then
        assertThatThrownBy(() -> importService.getAllClientsFromOldSystem())
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Failed to retrieve clients from old system");
        
        verify(oldSystemClient).getAllClients();
    }
}
