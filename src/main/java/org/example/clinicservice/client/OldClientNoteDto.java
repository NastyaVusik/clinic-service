package org.example.clinicservice.client;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OldClientNoteDto {

    private String guid;
    
    @JsonProperty("clientGuid")
    private String clientGuid;
    
    private String comments;
    
    @JsonProperty("loggedUser")
    private String loggedUser;
    
    @JsonProperty("createdDateTime")
    private String createdDateTime;
    
    @JsonProperty("modifiedDateTime")
    private String modifiedDateTime;
    
    private String datetime;
}
