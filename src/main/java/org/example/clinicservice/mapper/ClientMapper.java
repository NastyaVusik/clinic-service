package org.example.clinicservice.mapper;

import org.example.clinicservice.client.dto.OldClientDto;
import org.example.clinicservice.client.dto.ClientResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ClientMapper {

    @Mapping(target = "guid", source = "guid")
    @Mapping(target = "agency", source = "agency")
    @Mapping(source = "firstName", target = "firstName")
    @Mapping(source = "lastName", target = "lastName")
    ClientResponseDto toResponseDto(OldClientDto oldClientDto);
    
    List<ClientResponseDto> toResponseDtoList(List<OldClientDto> oldClientDtoList);
}
