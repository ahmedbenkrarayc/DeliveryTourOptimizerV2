package com.deliverytouroptimizer.deliverytouroptimizerv2.repository;

import com.deliverytouroptimizer.deliverytouroptimizerv2.model.Warehouse;
import io.micrometer.common.lang.NonNull;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WarehouseRepository extends JpaRepository<Warehouse, Long> {
    @EntityGraph(attributePaths = {"tours"})
    @NonNull
    Optional<Warehouse> findById(@NonNull Long id);

    @EntityGraph(attributePaths = {"tours"})
    @NonNull
    List<Warehouse> findAll();
}
