package org.example.clinicservice.util;

import org.example.clinicservice.client.dto.OldClientNoteDto;

import java.util.List;

/**
 * Test data factory for OldClientNoteDto objects.
 */
public class OldClientNoteDtoTestData {

    public static OldClientNoteDto createDefault() {
        return OldClientNoteDto.builder()
                .guid("20CBCEDA-3764-7F20-0BB6-4D6DD46BA9F8")
                .clientGuid("01588E84-D45A-EB98-F47F-716073A4F1EF")
                .comments("Patient Care Coordinator, reached out to patient caregiver is still in the hospital.")
                .loggedUser("p.vasya")
                .createdDateTime("2021-11-15 11:51:59")
                .modifiedDateTime("2021-11-15 11:51:59")
                .datetime("2021-09-16 12:02:26 CDT")
                .build();
    }

    public static OldClientNoteDto createWithGuid(String guid) {
        return createDefault().toBuilder()
                .guid(guid)
                .build();
    }

    public static OldClientNoteDto createWithClientGuid(String clientGuid) {
        return createDefault().toBuilder()
                .clientGuid(clientGuid)
                .build();
    }

    public static OldClientNoteDto createWithUser(String loggedUser) {
        return createDefault().toBuilder()
                .loggedUser(loggedUser)
                .build();
    }

    public static OldClientNoteDto createWithModifiedDateTime(String modifiedDateTime) {
        return createDefault().toBuilder()
                .modifiedDateTime(modifiedDateTime)
                .build();
    }

    public static OldClientNoteDto createNewer() {
        return createDefault().toBuilder()
                .modifiedDateTime("2022-01-01 10:00:00")
                .build();
    }

    public static OldClientNoteDto createOlder() {
        return createDefault().toBuilder()
                .modifiedDateTime("2020-01-01 10:00:00")
                .build();
    }

    public static OldClientNoteDto createWithComments(String comments) {
        return createDefault().toBuilder()
                .comments(comments)
                .build();
    }

    public static List<OldClientNoteDto> createMultipleNotes() {
        return List.of(
                createDefault(),
                createWithGuid("note-guid-2").toBuilder()
                        .comments("Second note content")
                        .loggedUser("second.user")
                        .build()
        );
    }

    public static List<OldClientNoteDto> createEmptyList() {
        return List.of();
    }
}
