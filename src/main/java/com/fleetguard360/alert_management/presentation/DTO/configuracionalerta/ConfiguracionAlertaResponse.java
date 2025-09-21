package com.fleetguard360.alert_management.presentation.DTO.configuracionalerta;

import com.fleetguard360.alert_management.presentation.DTO.tipoalerta.TipoAlertaResponse;
import com.fleetguard360.alert_management.presentation.DTO.nivelprioridad.NivelPrioridadResponse;

public record ConfiguracionAlertaResponse(
        Integer id,
        TipoAlertaResponse tipoAlerta,
        NivelPrioridadResponse nivelPrioridad,
        Long usuarioResponsableId,
        String parametroDisparador
) {}
