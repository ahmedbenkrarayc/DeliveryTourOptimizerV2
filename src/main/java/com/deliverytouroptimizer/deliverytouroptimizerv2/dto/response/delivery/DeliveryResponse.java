package com.deliverytouroptimizer.deliverytouroptimizerv2.dto.response.delivery;

import com.deliverytouroptimizer.deliverytouroptimizerv2.dto.response.tour.TourResponse;
import com.deliverytouroptimizer.deliverytouroptimizerv2.model.Customer;
import com.deliverytouroptimizer.deliverytouroptimizerv2.model.enums.DeliveryStatus;

public record DeliveryResponse(
        Long id,
        String address,
        Double latitude,
        Double longitude,
        double weight,
        double volume,
        String preferredTimeSlot,
        DeliveryStatus status,
        TourResponse tour,
        Customer customer
) {
}
