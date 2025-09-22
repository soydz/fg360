package com.fleetguard360.infrastructure.feight.dto;

public record AuthResFeightDTO(
        String email,
        String message,
        String jwt,
        boolean status
) {
}
