package com.fleetguard360.alert_management.service.interfaces;

import com.fleetguard360.alert_management.persistence.entity.NivelPrioridad;
import com.fleetguard360.alert_management.presentation.DTO.NivelPrioridadCreateRequest;
import com.fleetguard360.alert_management.presentation.DTO.NivelPrioridadUpdateRequest;

import java.util.List;

public interface NivelPrioridadService {
    NivelPrioridad create(NivelPrioridadCreateRequest request);
    NivelPrioridad update(Integer id, NivelPrioridadUpdateRequest request);
    void delete(Integer id);
    NivelPrioridad getById(Integer id);
    List<NivelPrioridad> listAll();
}
