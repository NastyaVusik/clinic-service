package org.example.clinicservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.clinicservice.domain.CompanyUser;
import org.example.clinicservice.exception.InvalidLoginException;
import org.example.clinicservice.repository.CompanyUserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CompanyUserServiceImpl implements CompanyUserService {

    private final CompanyUserRepository companyUserRepository;

    @Transactional
    @Override
    public CompanyUser getOrCreateUser(String login) {
        if (login == null || login.trim().isEmpty()) {
            throw new InvalidLoginException("Login cannot be null or empty");
        }
        
        String trimmedLogin = login.trim();
        
        return companyUserRepository.findByLogin(trimmedLogin)
                .orElseGet(() -> {
                    log.info("Creating new company user with login: {}", trimmedLogin);
                    CompanyUser newUser = new CompanyUser();
                    newUser.setLogin(trimmedLogin);
                    return companyUserRepository.save(newUser);
                });
    }
}
