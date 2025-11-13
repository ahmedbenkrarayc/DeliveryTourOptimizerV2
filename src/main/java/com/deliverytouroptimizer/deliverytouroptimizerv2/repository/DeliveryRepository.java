package com.deliverytouroptimizer.deliverytouroptimizerv2.repository;

import com.deliverytouroptimizer.deliverytouroptimizerv2.model.Delivery;
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
public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
    @EntityGraph(attributePaths = {"customer" ,"tour", "tour.vehicle", "tour.warehouse"})
    @NonNull
    Optional<Delivery> findById(@NonNull Long id);

    @EntityGraph(attributePaths = {"customer" ,"tour", "tour.vehicle", "tour.warehouse"})
    @NonNull
    Page<Delivery> findAll(@NonNull Pageable pageable);

    @Query("""
        SELECT DISTINCT d FROM Delivery d
            LEFT JOIN d.customer c
            LEFT JOIN d.tour t
            WHERE (:search IS NULL OR :search = ''
                  OR LOWER(d.address) LIKE LOWER(CONCAT('%', :search, '%'))
                  OR LOWER(CAST(d.status AS string)) LIKE LOWER(CONCAT('%', :search, '%')))
              AND (:customerId IS NULL OR c.id = :customerId)
    """)
    Page<Delivery> searchDeliveries(
            @Param("search") String search,
            @Param("customerId") Long customerId,
            Pageable pageable
    );
}
