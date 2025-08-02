package org.example.clinicservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "old-system-client", url = "${old-system.host}")
public interface OldSystemClient {
    
    @PostMapping("/clients")
    List<OldClientDto> getAllClients();
    
    @PostMapping("/notes")
    List<OldClientNoteDto> getNotes(@RequestBody ClientNotesRequestDto request);
}