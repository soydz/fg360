package com.fleetguard360.alert_management.presentation.DTO.tipoalerta;

public record TipoAlertaResponse(
        Integer id,
        String nombre,
        String descripcion,
        boolean activo
) {}
