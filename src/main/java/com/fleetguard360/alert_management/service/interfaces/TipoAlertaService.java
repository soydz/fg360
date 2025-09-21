package com.fleetguard360.alert_management.service.interfaces;

import com.fleetguard360.alert_management.persistence.entity.TipoAlerta;
import com.fleetguard360.alert_management.presentation.DTO.TipoAlertaCreateRequest;
import com.fleetguard360.alert_management.presentation.DTO.TipoAlertaUpdateRequest;

import java.util.List;

public interface TipoAlertaService {
    TipoAlerta create(TipoAlertaCreateRequest request);
    TipoAlerta update(Integer id, TipoAlertaUpdateRequest request);
    void delete(Integer id);
    TipoAlerta getById(Integer id);
    List<TipoAlerta> listAll();
    TipoAlerta activate(Integer id);
    TipoAlerta deactivate(Integer id);
}
