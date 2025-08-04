package org.example.clinicservice.util;

import org.example.clinicservice.domain.PatientProfile;

import java.util.List;

/**
 * Test data factory for PatientProfile objects.
 */
public class PatientProfileTestData {

    public static PatientProfile createDefault() {
        PatientProfile patient = new PatientProfile();
        patient.setId(1L);
        patient.setFirstName("John");
        patient.setLastName("Doe");
        patient.setStatusId((short) 200);
        patient.setOldClientGuid("01588E84-D45A-EB98-F47F-716073A4F1EF");
        return patient;
    }

    public static PatientProfile createWithId(Long id) {
        PatientProfile patient = createDefault();
        patient.setId(id);
        return patient;
    }

    public static PatientProfile createWithStatus(short statusId) {
        PatientProfile patient = createDefault();
        patient.setStatusId(statusId);
        return patient;
    }

    public static PatientProfile createWithOldClientGuids(String oldClientGuids) {
        PatientProfile patient = createDefault();
        patient.setOldClientGuid(oldClientGuids);
        return patient;
    }

    public static PatientProfile createWithMultipleGuids() {
        return createWithOldClientGuids("test-guid-1,test-guid-2");
    }

    public static PatientProfile createWithoutGuids() {
        PatientProfile patient = createDefault();
        patient.setOldClientGuid(null);
        return patient;
    }

    public static PatientProfile createInactive() {
        return createWithStatus((short) 100);
    }

    public static PatientProfile createActive210() {
        return createWithStatus((short) 210);
    }

    public static PatientProfile createActive230() {
        return createWithStatus((short) 230);
    }

    public static List<PatientProfile> createMultiplePatients() {
        PatientProfile second = createWithId(2L);
        second.setFirstName("Jane");
        second.setLastName("Smith");
        second.setOldClientGuid("test-guid-3,test-guid-4");
        
        return List.of(createDefault(), second);
    }

    public static List<PatientProfile> createEmptyList() {
        return List.of();
    }
}
