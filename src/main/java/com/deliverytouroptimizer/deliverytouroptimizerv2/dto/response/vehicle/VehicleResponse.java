package com.deliverytouroptimizer.deliverytouroptimizerv2.dto.response.vehicle;

import com.deliverytouroptimizer.deliverytouroptimizerv2.model.enums.VehicleType;

public record VehicleResponse(
        Long id,
        VehicleType type,
        String registrationNumber
) {
}
