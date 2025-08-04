package org.example.clinicservice.client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Old system client DTO")
public class OldClientDto {
    
    @Schema(description = "Agency identifier", example = "vhh4")
    private String agency;
    
    @Schema(description = "Client unique identifier", example = "01588E84-D45A-EB98-F47F-716073A4F1EF")
    private String guid;
    
    @JsonProperty("firstName")
    @Schema(description = "Client first name", example = "John")
    private String firstName;
    
    @JsonProperty("lastName")
    @Schema(description = "Client last name", example = "Doe")
    private String lastName;
    
    @Schema(description = "Client status", example = "ACTIVE")
    private String status;
    
    @Schema(description = "Date of birth", example = "10-15-1999")
    private String dob;
    
    @JsonProperty("createdDateTime")
    @Schema(description = "Creation date and time", example = "2021-11-15 11:51:59")
    private String createdDateTime;
}