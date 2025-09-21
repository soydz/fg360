package com.fleetguard360.alert_management.presentation.DTO;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record ConfiguracionAlertaCreateRequest(
        @NotNull(message = "tipoAlertaId es obligatorio")
        @Positive(message = "tipoAlertaId debe ser positivo")
        Integer tipoAlertaId,
        @NotNull(message = "nivelPrioridadId es obligatorio")
        @Positive(message = "nivelPrioridadId debe ser positivo")
        Integer nivelPrioridadId,
        @NotNull(message = "usuarioResponsableId es obligatorio")
        @Positive(message = "usuarioResponsableId debe ser positivo")
        Long usuarioResponsableId,
        @Size(max = 255, message = "parametroDisparador debe tener máximo 255 caracteres")
        String parametroDisparador
) {}

