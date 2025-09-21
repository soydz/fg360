package com.fleetguard360.alert_management.presentation.controller;

import com.fleetguard360.alert_management.presentation.DTO.configuracionalerta.ConfiguracionAlertaCreateRequest;
import com.fleetguard360.alert_management.presentation.DTO.configuracionalerta.ConfiguracionAlertaResponse;
import com.fleetguard360.alert_management.presentation.DTO.configuracionalerta.ConfiguracionAlertaUpdateRequest;
import com.fleetguard360.alert_management.service.interfaces.ConfiguracionAlertaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ConfiguracionAlertaGraphQLController {

    private final ConfiguracionAlertaService configuracionAlertaService;

    @QueryMapping
    public ConfiguracionAlertaResponse configuracionAlerta(@Argument Integer id) {
        log.debug("GraphQL Query: configuracionAlerta id={}", id);
        return configuracionAlertaService.getById(id);
    }

    @QueryMapping
    public List<ConfiguracionAlertaResponse> configuracionesAlerta() {
        log.debug("GraphQL Query: configuracionesAlerta");
        return configuracionAlertaService.listAll();
    }

    @QueryMapping
    public List<ConfiguracionAlertaResponse> configuracionesByTipoAlertaId(@Argument Integer tipoAlertaId) {
        log.debug("GraphQL Query: configuracionesByTipoAlertaId tipoAlertaId={}", tipoAlertaId);
        return configuracionAlertaService.findByTipoAlertaId(tipoAlertaId);
    }

    @QueryMapping
    public List<ConfiguracionAlertaResponse> configuracionesByNivelPrioridadId(@Argument Integer nivelPrioridadId) {
        log.debug("GraphQL Query: configuracionesByNivelPrioridadId nivelPrioridadId={}", nivelPrioridadId);
        return configuracionAlertaService.findByNivelPrioridadId(nivelPrioridadId);
    }

    @QueryMapping
    public List<ConfiguracionAlertaResponse> configuracionesByUsuarioResponsableId(@Argument Long usuarioResponsableId) {
        log.debug("GraphQL Query: configuracionesByUsuarioResponsableId usuarioResponsableId={}", usuarioResponsableId);
        return configuracionAlertaService.findByUsuarioResponsableId(usuarioResponsableId);
    }

    @MutationMapping
    public ConfiguracionAlertaResponse createConfiguracionAlerta(@Argument("input") ConfiguracionAlertaCreateRequest input) {
        log.debug("GraphQL Mutation: createConfiguracionAlerta tipoAlertaId={} nivelPrioridadId={} usuarioResponsableId={}", input.tipoAlertaId(), input.nivelPrioridadId(), input.usuarioResponsableId());
        return configuracionAlertaService.create(input);
    }

    @MutationMapping
    public ConfiguracionAlertaResponse updateConfiguracionAlerta(@Argument Integer id, @Argument("input") ConfiguracionAlertaUpdateRequest input) {
        log.debug("GraphQL Mutation: updateConfiguracionAlerta id={}", id);
        return configuracionAlertaService.update(id, input);
    }

    @MutationMapping
    public Boolean deleteConfiguracionAlerta(@Argument Integer id) {
        log.debug("GraphQL Mutation: deleteConfiguracionAlerta id={}", id);
        configuracionAlertaService.delete(id);
        return true;
    }
}
