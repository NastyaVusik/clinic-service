package org.example.clinicservice.client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.example.clinicservice.TestcontainersConfiguration;
import org.example.clinicservice.client.dto.ClientNotesRequestDto;
import org.example.clinicservice.client.dto.OldClientDto;
import org.example.clinicservice.client.dto.OldClientNoteDto;
import org.example.clinicservice.util.ClientNotesRequestDtoTestData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.context.annotation.Import;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration test for OldSystemClient.
 * This test verifies that the Feign client DTOs correctly serialize/deserialize
 * the JSON responses from the old system.
 */
@SpringBootTest
@Import(TestcontainersConfiguration.class)
@TestPropertySource(properties = {
    "old-system.host=http://localhost:8081"
})
class OldSystemClientIntegrationTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testClientsDtoDeserialization() throws Exception {
        // Given - expected JSON response from old system
        String expectedJson = """
            [
                {
                    "agency": "vhh4",
                    "guid": "01588E84-D45A-EB98-F47F-716073A4F1EF",
                    "firstName": "Ne",
                    "lastName": "Abr",
                    "status": "INACTIVE",
                    "dob": "10-15-1999",
                    "createdDateTime": "2021-11-15 11:51:59"
                }
            ]
            """;

        // When - deserialize JSON to DTOs
        List<OldClientDto> clients = objectMapper.readValue(expectedJson, new TypeReference<List<OldClientDto>>() {});

        // Then - verify deserialization
        assertThat(clients).hasSize(1);
        
        OldClientDto client = clients.get(0);
        assertThat(client.getAgency()).isEqualTo("vhh4");
        assertThat(client.getGuid()).isEqualTo("01588E84-D45A-EB98-F47F-716073A4F1EF");
        assertThat(client.getFirstName()).isEqualTo("Ne");
        assertThat(client.getLastName()).isEqualTo("Abr");
        assertThat(client.getStatus()).isEqualTo("INACTIVE");
        assertThat(client.getDob()).isEqualTo("10-15-1999");
        assertThat(client.getCreatedDateTime()).isEqualTo("2021-11-15 11:51:59");
    }

    @Test
    void testNotesDtoDeserialization() throws Exception {
        // Given - expected JSON response from old system
        String expectedJson = """
            [
                {
                    "comments": "Patient Care Coordinator, reached out to patient caregiver is still in the hospital.",
                    "guid": "20CBCEDA-3764-7F20-0BB6-4D6DD46BA9F8",
                    "modifiedDateTime": "2021-11-15 11:51:59",
                    "clientGuid": "C5DCAA49-ADE5-E65C-B776-3F6D7B5F2055",
                    "datetime": "2021-09-16 12:02:26 CDT",
                    "loggedUser": "p.vasya",
                    "createdDateTime": "2021-11-15 11:51:59"
                }
            ]
            """;

        // When - deserialize JSON to DTOs
        List<OldClientNoteDto> notes = objectMapper.readValue(expectedJson, new TypeReference<List<OldClientNoteDto>>() {});

        // Then - verify deserialization
        assertThat(notes).hasSize(1);
        
        OldClientNoteDto note = notes.get(0);
        assertThat(note.getComments()).isEqualTo("Patient Care Coordinator, reached out to patient caregiver is still in the hospital.");
        assertThat(note.getGuid()).isEqualTo("20CBCEDA-3764-7F20-0BB6-4D6DD46BA9F8");
        assertThat(note.getModifiedDateTime()).isEqualTo("2021-11-15 11:51:59");
        assertThat(note.getClientGuid()).isEqualTo("C5DCAA49-ADE5-E65C-B776-3F6D7B5F2055");
        assertThat(note.getDatetime()).isEqualTo("2021-09-16 12:02:26 CDT");
        assertThat(note.getLoggedUser()).isEqualTo("p.vasya");
        assertThat(note.getCreatedDateTime()).isEqualTo("2021-11-15 11:51:59");
    }

    @Test
    void testNotesRequestDtoSerialization() throws Exception {
        // Given - request DTO
        ClientNotesRequestDto request = ClientNotesRequestDtoTestData.createDefault();

        // When - serialize DTO to JSON
        String json = objectMapper.writeValueAsString(request);

        // Then - verify JSON structure
        assertThat(json).contains("\"agency\":\"vhh4\"");
        assertThat(json).contains("\"dateFrom\":\"2019-09-18\"");
        assertThat(json).contains("\"dateTo\":\"2021-09-17\"");
        assertThat(json).contains("\"clientGuid\":\"01588E84-D45A-EB98-F47F-716073A4F1EF\"");

        // And verify deserialization works correctly
        ClientNotesRequestDto deserializedRequest = objectMapper.readValue(json, ClientNotesRequestDto.class);
        assertThat(deserializedRequest.getAgency()).isEqualTo("vhh4");
        assertThat(deserializedRequest.getDateFrom()).isEqualTo("2019-09-18");
        assertThat(deserializedRequest.getDateTo()).isEqualTo("2021-09-17");
        assertThat(deserializedRequest.getClientGuid()).isEqualTo("01588E84-D45A-EB98-F47F-716073A4F1EF");
    }

    @ParameterizedTest
    @MethodSource("provideEmptyResponseTestData")
    void testEmptyResponsesDeserialization(String jsonResponse, String testDescription) throws Exception {
        // When & Then
        if (testDescription.contains("clients")) {
            List<OldClientDto> result = objectMapper.readValue(jsonResponse, new TypeReference<List<OldClientDto>>() {});
            assertThat(result).isEmpty();
        } else {
            List<OldClientNoteDto> result = objectMapper.readValue(jsonResponse, new TypeReference<List<OldClientNoteDto>>() {});
            assertThat(result).isEmpty();
        }
    }

    @ParameterizedTest
    @MethodSource("provideMultipleClientsTestData")
    void testMultipleClientsDeserialization(String jsonData, int expectedCount) throws Exception {
        // When
        List<OldClientDto> clients = objectMapper.readValue(jsonData, new TypeReference<List<OldClientDto>>() {});

        // Then
        assertThat(clients).hasSize(expectedCount);
        
        if (expectedCount > 0) {
            assertThat(clients.get(0).getAgency()).isNotBlank();
            assertThat(clients.get(0).getGuid()).isNotBlank();
        }
    }

    @ParameterizedTest
    @MethodSource("provideSerializationTestData")
    void testRequestDtoSerialization(ClientNotesRequestDto request, String expectedField, String expectedValue) throws Exception {
        // When
        String json = objectMapper.writeValueAsString(request);

        // Then
        assertThat(json).contains("\"" + expectedField + "\":\"" + expectedValue + "\"");
    }

    static Stream<Arguments> provideEmptyResponseTestData() {
        return Stream.of(
                Arguments.of("[]", "empty clients response"),
                Arguments.of("[]", "empty notes response")
        );
    }

    static Stream<Arguments> provideMultipleClientsTestData() {
        return Stream.of(
                Arguments.of("[]", 0),
                Arguments.of("""
                    [
                        {
                            "agency": "vhh4",
                            "guid": "01588E84-D45A-EB98-F47F-716073A4F1EF",
                            "firstName": "John",
                            "lastName": "Doe",
                            "status": "ACTIVE",
                            "dob": "01-01-1990",
                            "createdDateTime": "2021-01-01 10:00:00"
                        }
                    ]
                    """, 1),
                Arguments.of("""
                    [
                        {
                            "agency": "vhh4",
                            "guid": "01588E84-D45A-EB98-F47F-716073A4F1EF",
                            "firstName": "John",
                            "lastName": "Doe",
                            "status": "ACTIVE",
                            "dob": "01-01-1990",
                            "createdDateTime": "2021-01-01 10:00:00"
                        },
                        {
                            "agency": "abc1",
                            "guid": "02588E84-D45A-EB98-F47F-716073A4F1EF",
                            "firstName": "Jane",
                            "lastName": "Smith",
                            "status": "INACTIVE",
                            "dob": "02-02-1985",
                            "createdDateTime": "2021-02-02 11:00:00"
                        }
                    ]
                    """, 2)
        );
    }

    static Stream<Arguments> provideSerializationTestData() {
        return Stream.of(
                Arguments.of(ClientNotesRequestDtoTestData.createDefault(), "agency", "vhh4"),
                Arguments.of(ClientNotesRequestDtoTestData.createDefault(), "dateFrom", "2019-09-18"),
                Arguments.of(ClientNotesRequestDtoTestData.createDefault(), "dateTo", "2021-09-17"),
                Arguments.of(ClientNotesRequestDtoTestData.createDefault(), "clientGuid", "01588E84-D45A-EB98-F47F-716073A4F1EF"),
                Arguments.of(ClientNotesRequestDtoTestData.createWithAgency("test-agency"), "agency", "test-agency"),
                Arguments.of(ClientNotesRequestDtoTestData.createForNonExistentClient(), "clientGuid", "non-existent-guid")
        );
    }
}
