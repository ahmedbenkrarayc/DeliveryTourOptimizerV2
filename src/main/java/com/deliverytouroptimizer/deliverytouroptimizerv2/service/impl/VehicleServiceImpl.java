package com.deliverytouroptimizer.deliverytouroptimizerv2.service.impl;

import com.deliverytouroptimizer.deliverytouroptimizerv2.dto.request.vehicle.CreateVehicleRequest;
import com.deliverytouroptimizer.deliverytouroptimizerv2.dto.request.vehicle.UpdateVehicleRequest;
import com.deliverytouroptimizer.deliverytouroptimizerv2.dto.response.vehicle.VehicleResponse;
import com.deliverytouroptimizer.deliverytouroptimizerv2.exception.ResourceNotFoundException;
import com.deliverytouroptimizer.deliverytouroptimizerv2.exception.UniqueResourceException;
import com.deliverytouroptimizer.deliverytouroptimizerv2.mapper.VehicleMapper;
import com.deliverytouroptimizer.deliverytouroptimizerv2.model.Vehicle;
import com.deliverytouroptimizer.deliverytouroptimizerv2.model.enums.VehicleType;
import com.deliverytouroptimizer.deliverytouroptimizerv2.repository.VehicleRepository;
import com.deliverytouroptimizer.deliverytouroptimizerv2.service.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class VehicleServiceImpl implements VehicleService {
    private final VehicleRepository vehicleRepository;
    private final VehicleMapper vehicleMapper;

    @Override
    public VehicleResponse create(CreateVehicleRequest request) {
        if(vehicleRepository.findByRegistrationNumber(request.registrationNumber()).isPresent())
            throw new UniqueResourceException("A vehicle already exists with this registration number: "+ request.registrationNumber());

        Vehicle vehicle = vehicleMapper.toEntity(request);
        Vehicle saved = vehicleRepository.save(vehicle);
        return vehicleMapper.toResponse(saved);
    }

    @Override
    public VehicleResponse update(Long id, UpdateVehicleRequest request) {
        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle not found: "+ id));

        if(!vehicle.getRegistrationNumber().equalsIgnoreCase(request.registrationNumber())){
            if(vehicleRepository.findByRegistrationNumber(request.registrationNumber()).isPresent())
                throw new UniqueResourceException("A vehicle already exists with this registration number: "+ request.registrationNumber());
        }

        vehicleMapper.updateEntityFromDto(request, vehicle);
        return vehicleMapper.toResponse(vehicle);
    }

    @Override
    public void delete(Long id) {
        if(!vehicleRepository.existsById(id)){
            throw new ResourceNotFoundException("Vehicle not found: "+ id);
        }
        vehicleRepository.deleteById(id);
    }

    @Override
    public VehicleResponse getById(Long id) {
        return vehicleRepository.findById(id)
                .map(vehicleMapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle not found: "+ id));
    }

    @Override
    public List<VehicleResponse> getAll() {
        return vehicleRepository.findAll()
                .stream()
                .map(vehicleMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<VehicleResponse> getByType(VehicleType type) {
        return vehicleRepository.findByType(type)
                .stream()
                .map(vehicleMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public VehicleResponse getByRegistrationNumber(String registrationNumber) {
        return vehicleRepository.findByRegistrationNumber(registrationNumber)
                .map(vehicleMapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle not found: "+ registrationNumber));
    }
}
