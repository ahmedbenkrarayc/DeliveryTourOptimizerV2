package com.deliverytouroptimizer.deliverytouroptimizerv2.dto.request.deliveryhistory;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;

public record UpdateDeliveryHistoryRequest(
        @NotNull(message = "Delivery date is required")
        @FutureOrPresent(message = "Delivery date cannot be in the past")
        LocalDate deliveryDate,
        @NotNull(message = "Planned time is required")
        LocalTime plannedTime,
        LocalTime actualTime,
        @NotNull(message = "Delivery ID is required")
        Long deliveryId
) {
}
