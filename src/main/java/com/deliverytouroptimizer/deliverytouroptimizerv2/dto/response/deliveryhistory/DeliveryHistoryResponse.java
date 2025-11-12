package com.deliverytouroptimizer.deliverytouroptimizerv2.dto.response.deliveryhistory;

import java.time.LocalDate;
import java.time.LocalTime;

public record DeliveryHistoryResponse(
        Long id,
        LocalDate deliveryDate,
        LocalTime plannedTime,
        LocalTime actualTime,
        Long deliveryId
) {
}
