package org.example.clinicservice.service;

import org.example.clinicservice.domain.CompanyUser;

public interface CompanyUserService {

    CompanyUser getOrCreateUser(String login);
}
