package com.deliverytouroptimizer.deliverytouroptimizerv2.dto.request.tour;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record CreateTourRequest(
        @NotNull(message = "Tour date is required")
        LocalDate date,
        @NotNull(message = "Warehouse ID is required")
        Long warehouseId,
        @NotNull(message = "Vehicle ID is required")
        Long vehicleId
) {
}
