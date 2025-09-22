package com.fleetguard360.presentation.graphql.dto;

import com.fleetguard360.infrastructure.feight.dto.AuthResFeightDTO;

public record AuthResDTO(
        String email,
        String message,
        String jwt,
        boolean status
) {

    public  static AuthResDTO toAuthResDTO(AuthResFeightDTO authResFeightDTO) {
        if (authResFeightDTO == null) throw new IllegalArgumentException();
        return new AuthResDTO(
                authResFeightDTO.email(),
                authResFeightDTO.message(),
                authResFeightDTO.jwt(),
                authResFeightDTO.status()
        );
    }
}
