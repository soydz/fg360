package com.fleetguard360.alert_management.presentation.DTO.configuracionalerta;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record ConfiguracionAlertaUpdateRequest(
        @Positive(message = "tipoAlertaId debe ser positivo")
        Integer tipoAlertaId,
        @Positive(message = "nivelPrioridadId debe ser positivo")
        Integer nivelPrioridadId,
        @Positive(message = "usuarioResponsableId debe ser positivo")
        Long usuarioResponsableId,
        @Size(max = 255, message = "parametroDisparador debe tener máximo 255 caracteres")
        String parametroDisparador
) {}
