package org.example.clinicservice.mapper;

import org.example.clinicservice.client.dto.ClientResponseDto;
import org.example.clinicservice.client.dto.OldClientDto;
import org.example.clinicservice.util.OldClientDtoTestData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ClientMapperTest {

    @Autowired
    private ClientMapper clientMapper;

    @Test
    void toResponseDto_shouldMapAllFields() {
        // Given
        OldClientDto oldClient = OldClientDtoTestData.createDefault();

        // When
        ClientResponseDto result = clientMapper.toResponseDto(oldClient);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getGuid()).isEqualTo(oldClient.getGuid());
        assertThat(result.getAgency()).isEqualTo(oldClient.getAgency());
    }

    @Test
    void toResponseDto_shouldHandleNullInput() {
        // When
        ClientResponseDto result = clientMapper.toResponseDto(null);

        // Then
        assertThat(result).isNull();
    }

    @ParameterizedTest
    @MethodSource("provideClientMappingTestData")
    void toResponseDto_shouldMapCorrectly(OldClientDto input, String expectedGuid, String expectedAgency) {
        // When
        ClientResponseDto result = clientMapper.toResponseDto(input);

        // Then
        if (input == null) {
            assertThat(result).isNull();
        } else {
            assertThat(result).isNotNull();
            assertThat(result.getGuid()).isEqualTo(expectedGuid);
            assertThat(result.getAgency()).isEqualTo(expectedAgency);
        }
    }

    @Test
    void toResponseDtoList_shouldMapAllItems() {
        // Given
        List<OldClientDto> oldClients = OldClientDtoTestData.createMultipleClients();

        // When
        List<ClientResponseDto> result = clientMapper.toResponseDtoList(oldClients);

        // Then
        assertThat(result).hasSize(2);
        
        ClientResponseDto firstResult = result.get(0);
        assertThat(firstResult.getGuid()).isEqualTo(oldClients.get(0).getGuid());
        assertThat(firstResult.getAgency()).isEqualTo(oldClients.get(0).getAgency());
        
        ClientResponseDto secondResult = result.get(1);
        assertThat(secondResult.getGuid()).isEqualTo(oldClients.get(1).getGuid());
        assertThat(secondResult.getAgency()).isEqualTo(oldClients.get(1).getAgency());
    }

    @ParameterizedTest
    @MethodSource("provideListMappingTestData")
    void toResponseDtoList_shouldHandleVariousInputs(List<OldClientDto> input, int expectedSize) {
        // When
        List<ClientResponseDto> result = clientMapper.toResponseDtoList(input);

        // Then
        if (input == null) {
            assertThat(result).isNull();
        } else {
            assertThat(result).hasSize(expectedSize);
        }
    }

    static Stream<Arguments> provideClientMappingTestData() {
        return Stream.of(
                Arguments.of(null, null, null),
                Arguments.of(OldClientDtoTestData.createDefault(), "01588E84-D45A-EB98-F47F-716073A4F1EF", "vhh4"),
                Arguments.of(OldClientDtoTestData.createPartial(), "03588E84-D45A-EB98-F47F-716073A4F1EF", "xyz9"),
                Arguments.of(OldClientDtoTestData.createWithEmptyFields(), "", ""),
                Arguments.of(OldClientDtoTestData.createInactive(), "01588E84-D45A-EB98-F47F-716073A4F1EF", "vhh4")
        );
    }

    static Stream<Arguments> provideListMappingTestData() {
        return Stream.of(
                Arguments.of(null, 0),
                Arguments.of(Collections.emptyList(), 0),
                Arguments.of(List.of(OldClientDtoTestData.createDefault()), 1),
                Arguments.of(OldClientDtoTestData.createMultipleClients(), 2)
        );
    }
}
