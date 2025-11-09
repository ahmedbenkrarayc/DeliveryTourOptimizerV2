package com.deliverytouroptimizer.deliverytouroptimizerv2.repository;

import com.deliverytouroptimizer.deliverytouroptimizerv2.model.Vehicle;
import com.deliverytouroptimizer.deliverytouroptimizerv2.model.enums.VehicleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    List<Vehicle> findByType(VehicleType registrationNumber);
    Optional<Vehicle> findByRegistrationNumber(String registrationNumber);
}
