package org.example.clinicservice.repository;

import org.example.clinicservice.domain.CompanyUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CompanyUserRepository extends JpaRepository<CompanyUser, Long> {

    Optional<CompanyUser> findByLogin(String login);
}
