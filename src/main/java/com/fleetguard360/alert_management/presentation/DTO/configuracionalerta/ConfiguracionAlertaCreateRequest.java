package com.fleetguard360.alert_management.presentation.DTO.configuracionalerta;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ConfiguracionAlertaCreateRequest(
        @NotNull(message = "tipoAlertaId es obligatorio")
        @Positive(message = "tipoAlertaId debe ser positivo")
        Integer tipoAlertaId,
        @NotNull(message = "usuarioResponsableId es obligatorio")
        @Positive(message = "usuarioResponsableId debe ser positivo")
        Long usuarioResponsableId
) {}
