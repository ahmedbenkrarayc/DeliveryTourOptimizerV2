package com.deliverytouroptimizer.deliverytouroptimizerv2.repository;

import com.deliverytouroptimizer.deliverytouroptimizerv2.model.Warehouse;
import io.micrometer.common.lang.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WarehouseRepository extends JpaRepository<Warehouse, Long> {
    @EntityGraph(attributePaths = {"tours"})
    @NonNull
    Optional<Warehouse> findById(@NonNull Long id);

    @EntityGraph(attributePaths = {"tours"})
    @NonNull
    Page<Warehouse> findAll(@NonNull Pageable pageable);

    @EntityGraph(attributePaths = {"tours"})
    @Query("""
        SELECT w FROM Warehouse w
        WHERE :search IS NULL OR :search = ''
        OR (LOWER(w.name) LIKE LOWER(CONCAT('%', :search, '%')))
        OR (LOWER(w.address) LIKE LOWER(CONCAT('%', :search, '%')))
    """)
    Page<Warehouse> searchWarehouses(@Param("search") String search,
                                     Pageable pageable);
}
