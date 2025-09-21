package com.fleetguard360.alert_management.presentation.DTO.common;

import java.time.OffsetDateTime;

public record ErrorResponse(
        OffsetDateTime timestamp,
        int status,
        String error,
        String message,
        String path,
        String traceId
) { }
