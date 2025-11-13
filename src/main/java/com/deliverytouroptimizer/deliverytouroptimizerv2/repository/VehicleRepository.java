package com.deliverytouroptimizer.deliverytouroptimizerv2.repository;

import com.deliverytouroptimizer.deliverytouroptimizerv2.model.Vehicle;
import com.deliverytouroptimizer.deliverytouroptimizerv2.model.enums.VehicleType;
import io.micrometer.common.lang.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    @NonNull
    Page<Vehicle> findAll(@NonNull Pageable pageable);
    Page<Vehicle> findByType(VehicleType registrationNumber, @NonNull Pageable pageable);
    Optional<Vehicle> findByRegistrationNumber(String registrationNumber);
}
