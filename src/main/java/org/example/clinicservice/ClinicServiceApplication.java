package org.example.clinicservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ClinicServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClinicServiceApplication.class, args);
    }

}
