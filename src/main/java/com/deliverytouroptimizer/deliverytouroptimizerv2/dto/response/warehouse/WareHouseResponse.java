package com.deliverytouroptimizer.deliverytouroptimizerv2.dto.response.warehouse;

import java.time.LocalTime;
import java.util.List;

public record WareHouseResponse(
        Long id,
        String name,
        String address,
        Double latitude,
        Double longitude,
        LocalTime openingTime,
        LocalTime closingTime,
        List<WareHouseTourResponse> tours
) {
}
