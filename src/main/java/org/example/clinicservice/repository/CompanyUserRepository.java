package org.example.clinicservice.repository;

import org.example.clinicservice.domain.CompanyUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyUserRepository extends JpaRepository<CompanyUser, Long> {

    public boolean existsByLogin(String login);
}
