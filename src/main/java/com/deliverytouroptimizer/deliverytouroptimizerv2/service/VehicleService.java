package com.deliverytouroptimizer.deliverytouroptimizerv2.service;

import com.deliverytouroptimizer.deliverytouroptimizerv2.dto.request.vehicle.CreateVehicleRequest;
import com.deliverytouroptimizer.deliverytouroptimizerv2.dto.request.vehicle.UpdateVehicleRequest;
import com.deliverytouroptimizer.deliverytouroptimizerv2.dto.response.vehicle.VehicleResponse;
import com.deliverytouroptimizer.deliverytouroptimizerv2.model.enums.VehicleType;

import java.util.List;

public interface VehicleService {
    VehicleResponse create(CreateVehicleRequest request);
    VehicleResponse update(Long id, UpdateVehicleRequest request);
    void delete(Long id);
    VehicleResponse getById(Long id);
    List<VehicleResponse> getAll();
    List<VehicleResponse> getByType(VehicleType type);
    VehicleResponse getByRegistrationNumber(String registrationNumber);
}
