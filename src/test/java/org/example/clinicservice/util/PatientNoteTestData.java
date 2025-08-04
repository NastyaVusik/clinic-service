package org.example.clinicservice.util;

import org.example.clinicservice.domain.PatientNote;
import org.example.clinicservice.domain.PatientProfile;
import org.example.clinicservice.domain.CompanyUser;

import java.time.LocalDateTime;

/**
 * Test data factory for PatientNote objects.
 */
public class PatientNoteTestData {

    public static PatientNote createDefault() {
        PatientNote note = new PatientNote();
        note.setId(1L);
        note.setNote("Test note content");
        note.setOldNoteGuid("note-guid-1");
        note.setCreatedDateTime(LocalDateTime.of(2021, 1, 1, 10, 0));
        note.setLastModifiedDateTime(LocalDateTime.of(2021, 1, 1, 10, 0));
        note.setPatient(PatientProfileTestData.createDefault());
        note.setCreatedBy(CompanyUserTestData.createDefault());
        note.setLastModifiedBy(CompanyUserTestData.createDefault());
        return note;
    }

    public static PatientNote createWithId(Long id) {
        PatientNote note = createDefault();
        note.setId(id);
        return note;
    }

    public static PatientNote createWithPatient(PatientProfile patient) {
        PatientNote note = createDefault();
        note.setPatient(patient);
        return note;
    }

    public static PatientNote createWithOldNoteGuid(String oldNoteGuid) {
        PatientNote note = createDefault();
        note.setOldNoteGuid(oldNoteGuid);
        return note;
    }

    public static PatientNote createWithContent(String content) {
        PatientNote note = createDefault();
        note.setNote(content);
        return note;
    }

    public static PatientNote createWithModifiedDateTime(LocalDateTime modifiedDateTime) {
        PatientNote note = createDefault();
        note.setLastModifiedDateTime(modifiedDateTime);
        return note;
    }

    public static PatientNote createOlderNote() {
        return createWithModifiedDateTime(LocalDateTime.of(2020, 1, 1, 10, 0));
    }

    public static PatientNote createNewerNote() {
        return createWithModifiedDateTime(LocalDateTime.of(2022, 1, 1, 10, 0));
    }

    public static PatientNote createWithCreatedBy(CompanyUser createdBy) {
        PatientNote note = createDefault();
        note.setCreatedBy(createdBy);
        return note;
    }

    public static PatientNote createWithModifiedBy(CompanyUser modifiedBy) {
        PatientNote note = createDefault();
        note.setLastModifiedBy(modifiedBy);
        return note;
    }
}
