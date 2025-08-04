package org.example.clinicservice.service;

import org.example.clinicservice.domain.CompanyUser;
import org.example.clinicservice.exception.InvalidLoginException;
import org.example.clinicservice.repository.CompanyUserRepository;
import org.example.clinicservice.util.CompanyUserTestData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CompanyUserServiceImplTest {

    @Mock
    private CompanyUserRepository companyUserRepository;

    @InjectMocks
    private CompanyUserServiceImpl companyUserService;

    @Test
    void getOrCreateUser_shouldReturnExistingUser() {
        // Given
        CompanyUser existingUser = CompanyUserTestData.createDefault();
        when(companyUserRepository.findByLogin(existingUser.getLogin())).thenReturn(Optional.of(existingUser));

        // When
        CompanyUser result = companyUserService.getOrCreateUser(existingUser.getLogin());

        // Then
        assertThat(result).isEqualTo(existingUser);
        verify(companyUserRepository).findByLogin(existingUser.getLogin());
        verify(companyUserRepository, never()).save(any(CompanyUser.class));
    }

    @Test
    void getOrCreateUser_shouldCreateNewUser() {
        // Given
        CompanyUser newUser = CompanyUserTestData.createNewUser();
        when(companyUserRepository.findByLogin(newUser.getLogin())).thenReturn(Optional.empty());
        when(companyUserRepository.save(any(CompanyUser.class))).thenReturn(newUser);

        // When
        CompanyUser result = companyUserService.getOrCreateUser(newUser.getLogin());

        // Then
        assertThat(result).isEqualTo(newUser);
        verify(companyUserRepository).findByLogin(newUser.getLogin());
        
        ArgumentCaptor<CompanyUser> userCaptor = ArgumentCaptor.forClass(CompanyUser.class);
        verify(companyUserRepository).save(userCaptor.capture());
        
        CompanyUser savedUser = userCaptor.getValue();
        assertThat(savedUser.getLogin()).isEqualTo(newUser.getLogin());
    }

    @ParameterizedTest
    @MethodSource("org.example.clinicservice.util.TestParameters#loginTrimmingParameters")
    void getOrCreateUser_shouldTrimLogin(String inputLogin, String expectedLogin) {
        // Given
        CompanyUser user = CompanyUserTestData.createWithLogin(expectedLogin);
        when(companyUserRepository.findByLogin(expectedLogin)).thenReturn(Optional.of(user));

        // When
        CompanyUser result = companyUserService.getOrCreateUser(inputLogin);

        // Then
        assertThat(result).isEqualTo(user);
        verify(companyUserRepository).findByLogin(expectedLogin);
    }

    @ParameterizedTest
    @MethodSource("org.example.clinicservice.util.TestParameters#invalidLoginParameters")
    void getOrCreateUser_shouldThrowExceptionForInvalidLogin(String invalidLogin, String expectedMessage) {
        // When & Then
        assertThatThrownBy(() -> companyUserService.getOrCreateUser(invalidLogin))
                .isInstanceOf(InvalidLoginException.class)
                .hasMessage(expectedMessage);

        verify(companyUserRepository, never()).findByLogin(any());
        verify(companyUserRepository, never()).save(any());
    }

    @ParameterizedTest
    @MethodSource("org.example.clinicservice.util.TestParameters#specialLoginParameters")
    void getOrCreateUser_shouldCreateUserWithSpecialLogin(String specialLogin) {
        // Given
        CompanyUser newUser = CompanyUserTestData.createWithLogin(specialLogin);
        when(companyUserRepository.findByLogin(specialLogin)).thenReturn(Optional.empty());
        when(companyUserRepository.save(any(CompanyUser.class))).thenReturn(newUser);

        // When
        CompanyUser result = companyUserService.getOrCreateUser(specialLogin);

        // Then
        assertThat(result).isEqualTo(newUser);
        assertThat(result.getLogin()).isEqualTo(specialLogin);
        verify(companyUserRepository).findByLogin(specialLogin);
        verify(companyUserRepository).save(any(CompanyUser.class));
    }
}
