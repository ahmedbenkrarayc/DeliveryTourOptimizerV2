package com.deliverytouroptimizer.deliverytouroptimizerv2.repository;

import com.deliverytouroptimizer.deliverytouroptimizerv2.model.Delivery;
import io.micrometer.common.lang.NonNull;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
    @EntityGraph(attributePaths = {"customer" ,"tour", "tour.vehicle", "tour.warehouse"})
    @NonNull
    Optional<Delivery> findById(@NonNull Long id);

    @EntityGraph(attributePaths = {"customer" ,"tour", "tour.vehicle", "tour.warehouse"})
    @NonNull
    List<Delivery> findAll();
}
