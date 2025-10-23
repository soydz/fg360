package com.fleetguard360.alert_management.presentation.DTO;

import java.time.LocalDateTime;

public record AlertDTO(
        String[] toUsers,
        String alertType,
        String responsible,
        String generatingUnit,
        LocalDateTime generationDate
) {
}
