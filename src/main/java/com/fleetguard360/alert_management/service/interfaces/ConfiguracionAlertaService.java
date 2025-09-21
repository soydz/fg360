package com.fleetguard360.alert_management.service.interfaces;

import com.fleetguard360.alert_management.persistence.entity.ConfiguracionAlerta;
import com.fleetguard360.alert_management.presentation.DTO.ConfiguracionAlertaCreateRequest;
import com.fleetguard360.alert_management.presentation.DTO.ConfiguracionAlertaUpdateRequest;

import java.util.List;

public interface ConfiguracionAlertaService {
    ConfiguracionAlerta create(ConfiguracionAlertaCreateRequest request);
    ConfiguracionAlerta update(Integer id, ConfiguracionAlertaUpdateRequest request);
    void delete(Integer id);
    ConfiguracionAlerta getById(Integer id);
    List<ConfiguracionAlerta> listAll();

    List<ConfiguracionAlerta> findByTipoAlertaId(Integer tipoAlertaId);
    List<ConfiguracionAlerta> findByNivelPrioridadId(Integer nivelPrioridadId);
    List<ConfiguracionAlerta> findByUsuarioResponsableId(Long usuarioResponsableId);
}
