package com.fleetguard360.alert_management.service.interfaces;

import com.fleetguard360.alert_management.presentation.DTO.configuracionalerta.ConfiguracionAlertaCreateRequest;
import com.fleetguard360.alert_management.presentation.DTO.configuracionalerta.ConfiguracionAlertaResponse;
import com.fleetguard360.alert_management.presentation.DTO.configuracionalerta.ConfiguracionAlertaUpdateRequest;

import java.util.List;

public interface ConfiguracionAlertaService {
    ConfiguracionAlertaResponse create(ConfiguracionAlertaCreateRequest request);
    ConfiguracionAlertaResponse update(Integer id, ConfiguracionAlertaUpdateRequest request);
    void delete(Integer id);
    ConfiguracionAlertaResponse getById(Integer id);
    List<ConfiguracionAlertaResponse> listAll();

    List<ConfiguracionAlertaResponse> findByTipoAlertaId(Integer tipoAlertaId);
    List<ConfiguracionAlertaResponse> findByUsuarioResponsableId(Long usuarioResponsableId);
}
