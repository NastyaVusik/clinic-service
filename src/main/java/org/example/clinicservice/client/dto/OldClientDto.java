package org.example.clinicservice.client;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OldClientDto {
    private String agency;
    private String guid;
    
    @JsonProperty("firstName")
    private String firstName;
    
    @JsonProperty("lastName")
    private String lastName;
    
    private String status;
    private String dob;
    
    @JsonProperty("createdDateTime")
    private String createdDateTime;
}