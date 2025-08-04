package org.example.clinicservice.client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Old system client note DTO")
public class OldClientNoteDto {

    @Schema(description = "Note unique identifier", example = "20CBCEDA-3764-7F20-0BB6-4D6DD46BA9F8")
    private String guid;
    
    @JsonProperty("clientGuid")
    @Schema(description = "Client unique identifier", example = "C5DCAA49-ADE5-E65C-B776-3F6D7B5F2055")
    private String clientGuid;
    
    @Schema(description = "Note content", example = "Patient Care Coordinator, reached out to patient caregiver is still in the hospital.")
    private String comments;
    
    @JsonProperty("loggedUser")
    @Schema(description = "User who created the note", example = "p.vasya")
    private String loggedUser;
    
    @JsonProperty("createdDateTime")
    @Schema(description = "Creation date and time", example = "2021-11-15 11:51:59")
    private String createdDateTime;
    
    @JsonProperty("modifiedDateTime")
    @Schema(description = "Last modification date and time", example = "2021-11-15 11:51:59")
    private String modifiedDateTime;
    
    @Schema(description = "Note datetime", example = "2021-09-16 12:02:26 CDT")
    private String datetime;
}
