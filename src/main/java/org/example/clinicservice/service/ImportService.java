package org.example.clinicservice.service;

import org.example.clinicservice.client.dto.ClientResponseDto;

import java.util.List;

public interface ImportService {
    void importClientsNotes();
    List<ClientResponseDto> getAllClientsFromOldSystem();
}
