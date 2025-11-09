package com.deliverytouroptimizer.deliverytouroptimizerv2.dto.response.tour;

import com.deliverytouroptimizer.deliverytouroptimizerv2.dto.response.vehicle.VehicleResponse;
import com.deliverytouroptimizer.deliverytouroptimizerv2.dto.response.warehouse.WareHouseResponse;

import java.time.LocalDate;

public record TourResponse(
        Long id,
        LocalDate date,
        WareHouseResponse warehouse,
        VehicleResponse vehicle
) {
}
