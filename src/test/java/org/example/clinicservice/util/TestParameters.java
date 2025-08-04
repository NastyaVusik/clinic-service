package org.example.clinicservice.util;

import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

/**
 * Test parameters provider for parameterized tests.
 */
public class TestParameters {

    /**
     * Provides invalid login parameters for CompanyUserService tests.
     */
    public static Stream<Arguments> invalidLoginParameters() {
        return Stream.of(
                Arguments.of(null, "Login cannot be null or empty"),
                Arguments.of("", "Login cannot be null or empty"),
                Arguments.of("   ", "Login cannot be null or empty")
        );
    }

    /**
     * Provides valid login parameters with trimming scenarios.
     */
    public static Stream<Arguments> loginTrimmingParameters() {
        return Stream.of(
                Arguments.of("test.user", "test.user"),
                Arguments.of("  test.user  ", "test.user"),
                Arguments.of("\ttest.user\n", "test.user"),
                Arguments.of(" user@domain.com ", "user@domain.com")
        );
    }

    /**
     * Provides special login scenarios.
     */
    public static Stream<Arguments> specialLoginParameters() {
        return Stream.of(
                Arguments.of("very.long.login.name.that.should.still.work"),
                Arguments.of("user@domain.com"),
                Arguments.of("user-123"),
                Arguments.of("user_name"),
                Arguments.of("123user")
        );
    }

    /**
     * Provides patient status parameters for active patients.
     */
    public static Stream<Arguments> activePatientStatusParameters() {
        return Stream.of(
                Arguments.of((short) 200),
                Arguments.of((short) 210),
                Arguments.of((short) 230)
        );
    }

    /**
     * Provides patient status parameters for inactive patients.
     */
    public static Stream<Arguments> inactivePatientStatusParameters() {
        return Stream.of(
                Arguments.of((short) 100),
                Arguments.of((short) 150),
                Arguments.of((short) 300)
        );
    }

    /**
     * Provides old client GUID variations.
     */
    public static Stream<Arguments> oldClientGuidParameters() {
        return Stream.of(
                Arguments.of("single-guid", new String[]{"single-guid"}),
                Arguments.of("guid1,guid2", new String[]{"guid1", "guid2"}),
                Arguments.of("guid1,guid2,guid3", new String[]{"guid1", "guid2", "guid3"}),
                Arguments.of(" guid1 , guid2 ", new String[]{"guid1", "guid2"})
        );
    }

    /**
     * Provides test data for JSON serialization/deserialization tests.
     */
    public static Stream<Arguments> jsonTestParameters() {
        return Stream.of(
                Arguments.of("empty array", "[]"),
                Arguments.of("single object", "[{\"field\":\"value\"}]"),
                Arguments.of("multiple objects", "[{\"field1\":\"value1\"},{\"field2\":\"value2\"}]")
        );
    }

    /**
     * Provides date time format variations for parsing tests.
     */
    public static Stream<Arguments> dateTimeFormatParameters() {
        return Stream.of(
                Arguments.of("2021-11-15 11:51:59", "Standard format"),
                Arguments.of("2021-09-16 12:02:26 CDT", "With timezone"),
                Arguments.of("2021-09-16 12:02:26 CST", "With CST timezone"),
                Arguments.of("", "Empty string"),
                Arguments.of(null, "Null value")
        );
    }

    /**
     * Provides agency and client GUID combinations.
     */
    public static Stream<Arguments> agencyClientParameters() {
        return Stream.of(
                Arguments.of("vhh4", "01588E84-D45A-EB98-F47F-716073A4F1EF"),
                Arguments.of("abc1", "02588E84-D45A-EB98-F47F-716073A4F1EF"),
                Arguments.of("xyz9", "03588E84-D45A-EB98-F47F-716073A4F1EF")
        );
    }
}
