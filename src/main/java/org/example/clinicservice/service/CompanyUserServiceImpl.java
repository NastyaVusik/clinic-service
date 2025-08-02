package org.example.clinicservice.service;

import lombok.RequiredArgsConstructor;
import org.example.clinicservice.domain.CompanyUser;
import org.example.clinicservice.repository.CompanyUserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CompanyUserServiceImpl implements CompanyUserService {

    private final CompanyUserRepository companyUserRepository;

    @Override
    public CompanyUser getOrCreateUser(String login) {
        if (login == null || login.trim().isEmpty()) {
            return null;
        }
        
        Optional<CompanyUser> existingUser = companyUserRepository.findByLogin(login);
        if (existingUser.isPresent()) {
            return existingUser.get();
        }
        
        CompanyUser newUser = new CompanyUser();
        newUser.setLogin(login);
        return companyUserRepository.save(newUser);
    }
}
