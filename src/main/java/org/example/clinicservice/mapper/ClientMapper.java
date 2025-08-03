package org.example.clinicservice.mapper;

import org.example.clinicservice.client.OldClientDto;
import org.example.clinicservice.client.dto.ClientResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ClientMapper {
    
    ClientResponseDto toResponseDto(OldClientDto oldClientDto);
    
    List<ClientResponseDto> toResponseDtoList(List<OldClientDto> oldClientDtoList);
}
