package org.example.clinicservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.clinicservice.service.ImportService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/import")
@RequiredArgsConstructor
@Slf4j
public class ImportController {
    
    private final ImportService importService;
    
    @PostMapping("/notes")
    public ResponseEntity<String> importNotes() {
        log.info("Manual import requested");
        
        try {
            importService.importClientsNotes();
            return ResponseEntity.ok("Import completed successfully");
        } catch (Exception e) {
            log.error("Manual import failed: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError().body("Import failed: " + e.getMessage());
        }
    }
}