package com.fleetguard360.alert_management.presentation.DTO.configuracionalerta;

import jakarta.validation.constraints.Positive;

public record ConfiguracionAlertaUpdateRequest(
        @Positive(message = "tipoAlertaId debe ser positivo")
        Integer tipoAlertaId,
        @Positive(message = "usuarioResponsableId debe ser positivo")
        Long usuarioResponsableId
) {}
