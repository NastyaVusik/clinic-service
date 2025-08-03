package org.example.clinicservice.client.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientResponseDto {
    private String guid;
    private String agency;
    private String name;
}
