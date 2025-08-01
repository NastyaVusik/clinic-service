package org.example.clinicservice.service;

import lombok.RequiredArgsConstructor;
import org.example.clinicservice.domain.CompanyUser;
import org.example.clinicservice.repository.CompanyUserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompanyUserServiceImpl {

    private final CompanyUserRepository companyUserRepository;

    public void createCompanyUser(CompanyUser companyUser) {
        if (!companyUserRepository.existsByLogin(companyUser.getLogin())) {
            companyUserRepository.save(companyUser);
        }
    }
}
