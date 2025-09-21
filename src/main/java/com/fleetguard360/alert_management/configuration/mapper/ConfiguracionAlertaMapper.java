package com.fleetguard360.alert_management.configuration.mapper;

import com.fleetguard360.alert_management.persistence.entity.ConfiguracionAlerta;
import com.fleetguard360.alert_management.presentation.DTO.configuracionalerta.ConfiguracionAlertaResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {TipoAlertaMapper.class, NivelPrioridadMapper.class})
public interface ConfiguracionAlertaMapper {
    ConfiguracionAlertaResponse toResponse(ConfiguracionAlerta entity);
    List<ConfiguracionAlertaResponse> toResponses(List<ConfiguracionAlerta> entities);
}
