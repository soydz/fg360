package com.fleetguard360.presentation.dto;

public record AuthResDTO(
    Long user,
    String email,
    String message,
    String jwt,
    boolean status
) {}
