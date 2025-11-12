package com.deliverytouroptimizer.deliverytouroptimizerv2.repository;

import com.deliverytouroptimizer.deliverytouroptimizerv2.model.DeliveryHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DeliveryHistoryRepository extends JpaRepository<DeliveryHistory, Long> {
    Optional<DeliveryHistory> findByDelivery_Id(Long id);
}
