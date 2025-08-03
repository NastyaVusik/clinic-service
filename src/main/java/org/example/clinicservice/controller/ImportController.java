package org.example.clinicservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.clinicservice.client.dto.ClientResponseDto;
import org.example.clinicservice.service.ImportService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/import")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Import", description = "Import operations from old system")
public class ImportController {

    private final ImportService importService;

    @GetMapping("/clients")
    @Operation(summary = "Get all clients from old system", 
               description = "Retrieves list of all clients from the legacy system")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved clients"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<ClientResponseDto>> getAllClients() {
        log.info("Getting all clients from old system");
        List<ClientResponseDto> clients = importService.getAllClientsFromOldSystem();
        return ResponseEntity.ok(clients);
    }

    @PostMapping("/notes")
    @Operation(summary = "Import client notes", 
               description = "Imports notes for all active patients from the old system")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Import process completed successfully"),
        @ApiResponse(responseCode = "500", description = "Internal server error during import")
    })
    public ResponseEntity<String> importClientNotes() {
        log.info("Starting import of client notes");
        importService.importClientsNotes();
        return ResponseEntity.ok("Import process completed successfully");
    }
}