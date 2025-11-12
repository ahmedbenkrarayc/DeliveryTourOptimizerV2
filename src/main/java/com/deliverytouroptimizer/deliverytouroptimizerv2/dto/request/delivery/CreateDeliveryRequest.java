package com.deliverytouroptimizer.deliverytouroptimizerv2.dto.request.delivery;

import com.deliverytouroptimizer.deliverytouroptimizerv2.model.enums.DeliveryStatus;
import jakarta.validation.constraints.*;

public record CreateDeliveryRequest(
        @NotBlank(message = "Delivery address cannot be blank")
        String address,
        @NotNull(message = "Latitude is required")
        @DecimalMin(value = "-90.0", message = "Latitude must be >= -90")
        @DecimalMax(value = "90.0", message = "Latitude must be <= 90")
        Double latitude,
        @NotNull(message = "Longitude is required")
        @DecimalMin(value = "-180.0", message = "Longitude must be >= -180")
        @DecimalMax(value = "180.0", message = "Longitude must be <= 180")
        Double longitude,
        @Min(value = 0, message = "Weight must be zero or positive")
        double weight,
        @Min(value = 0, message = "Volume must be zero or positive")
        double volume,
        String preferredTimeSlot,
        @NotNull(message = "Delivery status is required")
        DeliveryStatus status,
        @NotNull(message = "Tour ID is required")
        Long tourId,
        @NotNull(message = "Customer ID is required")
        Long customerId
) {
}
