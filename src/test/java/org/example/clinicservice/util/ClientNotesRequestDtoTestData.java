package org.example.clinicservice.util;

import org.example.clinicservice.client.dto.ClientNotesRequestDto;

/**
 * Test data factory for ClientNotesRequestDto objects.
 */
public class ClientNotesRequestDtoTestData {

    public static ClientNotesRequestDto createDefault() {
        return ClientNotesRequestDto.builder()
                .agency("vhh4")
                .dateFrom("2019-09-18")
                .dateTo("2021-09-17")
                .clientGuid("01588E84-D45A-EB98-F47F-716073A4F1EF")
                .build();
    }

    public static ClientNotesRequestDto createWithAgency(String agency) {
        return createDefault().toBuilder()
                .agency(agency)
                .build();
    }

    public static ClientNotesRequestDto createWithClientGuid(String clientGuid) {
        return createDefault().toBuilder()
                .clientGuid(clientGuid)
                .build();
    }

    public static ClientNotesRequestDto createWithDateRange(String dateFrom, String dateTo) {
        return createDefault().toBuilder()
                .dateFrom(dateFrom)
                .dateTo(dateTo)
                .build();
    }

    public static ClientNotesRequestDto createForNonExistentClient() {
        return createDefault().toBuilder()
                .agency("test-agency")
                .dateFrom("2021-01-01")
                .dateTo("2021-12-31")
                .clientGuid("non-existent-guid")
                .build();
    }
}
