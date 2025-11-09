package com.deliverytouroptimizer.deliverytouroptimizerv2.dto.request.warehouse;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalTime;

public record CreateWareHouseRequest(
        @NotBlank(message = "Warehouse name cannot be blank")
        String name,
        @NotBlank(message = "Warehouse address cannot be blank")
        String address,
        @NotNull(message = "Latitude is required")
        @DecimalMin(value = "-90.0", message = "Latitude must be >= -90")
        @DecimalMax(value = "90.0", message = "Latitude must be <= 90")
        Double latitude,
        @NotNull(message = "Longitude is required")
        @DecimalMin(value = "-180.0", message = "Longitude must be >= -180")
        @DecimalMax(value = "180.0", message = "Longitude must be <= 180")
        Double longitude,
        @NotNull(message = "Warehouse opening time is required")
        LocalTime openingTime,
        @NotNull(message = "Warehouse closing time is required")
        LocalTime closingTime
) {
}
