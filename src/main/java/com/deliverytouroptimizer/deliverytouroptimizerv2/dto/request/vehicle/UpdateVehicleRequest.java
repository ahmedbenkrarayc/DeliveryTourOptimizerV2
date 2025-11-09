package com.deliverytouroptimizer.deliverytouroptimizerv2.dto.request.vehicle;

import com.deliverytouroptimizer.deliverytouroptimizerv2.model.enums.VehicleType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateVehicleRequest(
        @NotNull(message = "Vehicle type cannot be blank")
        VehicleType type,
        @NotBlank(message = "Registration number is required")
        String registrationNumber
) {
}
