package org.example.clinicservice.util;

import org.example.clinicservice.client.dto.OldClientDto;

import java.util.List;

/**
 * Test data factory for OldClientDto objects.
 */
public class OldClientDtoTestData {

    public static OldClientDto createDefault() {
        return OldClientDto.builder()
                .guid("01588E84-D45A-EB98-F47F-716073A4F1EF")
                .agency("vhh4")
                .firstName("John")
                .lastName("Doe")
                .status("ACTIVE")
                .dob("01-01-1990")
                .createdDateTime("2021-11-15 11:51:59")
                .build();
    }

    public static OldClientDto createWithGuid(String guid) {
        return createDefault().toBuilder()
                .guid(guid)
                .build();
    }

    public static OldClientDto createWithAgency(String agency) {
        return createDefault().toBuilder()
                .agency(agency)
                .build();
    }

    public static OldClientDto createWithNameAndStatus(String firstName, String lastName, String status) {
        return createDefault().toBuilder()
                .firstName(firstName)
                .lastName(lastName)
                .status(status)
                .build();
    }

    public static OldClientDto createInactive() {
        return createDefault().toBuilder()
                .firstName("Ne")
                .lastName("Abr")
                .status("INACTIVE")
                .dob("10-15-1999")
                .build();
    }

    public static OldClientDto createSecondClient() {
        return OldClientDto.builder()
                .guid("02588E84-D45A-EB98-F47F-716073A4F1EF")
                .agency("abc1")
                .firstName("Jane")
                .lastName("Smith")
                .status("INACTIVE")
                .dob("02-02-1985")
                .createdDateTime("2021-12-15 12:51:59")
                .build();
    }

    public static OldClientDto createPartial() {
        return OldClientDto.builder()
                .guid("03588E84-D45A-EB98-F47F-716073A4F1EF")
                .agency("xyz9")
                .build();
    }

    public static OldClientDto createWithEmptyFields() {
        return OldClientDto.builder()
                .guid("")
                .agency("")
                .firstName("")
                .lastName("")
                .status("")
                .dob("")
                .createdDateTime("")
                .build();
    }

    public static List<OldClientDto> createMultipleClients() {
        return List.of(createDefault(), createSecondClient());
    }

    public static List<OldClientDto> createEmptyList() {
        return List.of();
    }
}
