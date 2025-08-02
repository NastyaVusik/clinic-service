package org.example.clinicservice.client;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientNotesRequestDto {
    private String agency;
    private String dateFrom;
    private String dateTo;
    private String clientGuid;
}
