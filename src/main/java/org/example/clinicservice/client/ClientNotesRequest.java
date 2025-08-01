package org.example.clinicservice.client;

import lombok.Builder;

import java.util.List;

@Builder
public class ClientNotesRequest {
    private String agency;
    private List<String> clientGuids;
}
