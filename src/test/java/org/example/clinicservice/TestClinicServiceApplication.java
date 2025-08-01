package org.example.clinicservice;

import org.springframework.boot.SpringApplication;

public class TestClinicServiceApplication {

    public static void main(String[] args) {
        SpringApplication.from(ClinicServiceApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
