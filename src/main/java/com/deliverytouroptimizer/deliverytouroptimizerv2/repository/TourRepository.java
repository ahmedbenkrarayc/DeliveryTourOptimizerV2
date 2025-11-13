package com.deliverytouroptimizer.deliverytouroptimizerv2.repository;

import com.deliverytouroptimizer.deliverytouroptimizerv2.model.Tour;
import io.micrometer.common.lang.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TourRepository extends JpaRepository<Tour, Long> {
    @EntityGraph(attributePaths = {"vehicle", "warehouse"})
    @NonNull
    Optional<Tour> findById(@NonNull Long id);

    @EntityGraph(attributePaths = {"vehicle", "warehouse"})
    @NonNull
    Page<Tour> findAll(@NonNull Pageable pageable);
}