package org.example.clinicservice.client.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Client response DTO")
public class ClientResponseDto {
    
    @Schema(description = "Client unique identifier", example = "01588E84-D45A-EB98-F47F-716073A4F1EF")
    private String guid;
    
    @Schema(description = "Agency identifier", example = "vhh4")
    private String agency;
    
    @Schema(description = "Client full name", example = "John Doe")
    private String name;
}
