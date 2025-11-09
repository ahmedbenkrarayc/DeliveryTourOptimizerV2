package com.deliverytouroptimizer.deliverytouroptimizerv2.dto.response.warehouse;

import java.time.LocalDate;

public record WareHouseTourResponse(
        Long id,
        LocalDate date,
        Long vehicleId
) {
}
