package com.fleetguard360.alert_management.presentation.DTO.configuracionalerta;

import com.fleetguard360.alert_management.presentation.DTO.tipoalerta.TipoAlertaResponse;

public record ConfiguracionAlertaResponse(
        Integer id,
        TipoAlertaResponse tipoAlerta,
        Long usuarioResponsableId
) {}
