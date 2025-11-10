package com.deliverytouroptimizer.deliverytouroptimizerv2.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeliveryHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull(message = "Delivery date is required")
    @PastOrPresent(message = "Delivery date cannot be in the future")
    private LocalDate deliveryDate;

    @NotNull(message = "Planned time is required")
    private LocalTime plannedTime;

    @NotNull(message = "Actual time is required")
    private LocalTime actualTime;
}
