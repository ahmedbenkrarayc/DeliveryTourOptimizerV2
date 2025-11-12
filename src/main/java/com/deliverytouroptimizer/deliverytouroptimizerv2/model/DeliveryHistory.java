package com.deliverytouroptimizer.deliverytouroptimizerv2.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
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
@Table(name = "deliveryhistory")
public class DeliveryHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull(message = "Delivery date is required")
    @PastOrPresent(message = "Delivery date cannot be in the future")
    @Column(name = "deliverydate")
    private LocalDate deliveryDate;

    @NotNull(message = "Planned time is required")
    @Column(name = "plannedtime")
    private LocalTime plannedTime;

    @NotNull(message = "Actual time is required")
    @Column(name = "actualtime")
    private LocalTime actualTime;

    @OneToOne(fetch= FetchType.LAZY)
    @JoinColumn(name = "delivery_id")
    @JsonBackReference
    private Delivery delivery;
}
