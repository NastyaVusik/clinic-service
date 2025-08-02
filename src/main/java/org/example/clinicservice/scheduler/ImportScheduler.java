package org.example.clinicservice.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.clinicservice.service.ImportService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ImportScheduler {
    
    private final ImportService importService;
    
    // Run every 2 hours at 15 minutes past the hour: 01:15, 03:15, 05:15, etc.
    @Scheduled(cron = "${import.notes.cron:0 15 */2 * * *}")
    public void scheduleNotesImport() {
        log.info("Starting scheduled notes import");
        
        try {
            importService.importClientsNotes();
            log.info("Scheduled import completed successfully");
        } catch (Exception e) {
            log.error("Scheduled import failed: {}", e.getMessage(), e);
        }
    }
}