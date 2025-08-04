package org.example.clinicservice.client.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request DTO for getting client notes from old system")
public class ClientNotesRequestDto {
    
    @Schema(description = "Agency identifier", example = "vhh4", required = true)
    private String agency;
    
    @Schema(description = "Start date for notes query", example = "2019-09-18", required = true)
    private String dateFrom;
    
    @Schema(description = "End date for notes query", example = "2021-09-17", required = true)
    private String dateTo;
    
    @Schema(description = "Client GUID", example = "01588E84-D45A-EB98-F47F-716073A4F1EF", required = true)
    private String clientGuid;
}
