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
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class VehicleServiceImpl implements VehicleService {
    private final VehicleRepository vehicleRepository;
    private final VehicleMapper vehicleMapper;

    @Override
    public VehicleResponse create(CreateVehicleRequest request) {
        log.info("Creating new vehicle with registration number: {}", request.registrationNumber());

        if(vehicleRepository.findByRegistrationNumber(request.registrationNumber()).isPresent()){
            log.error("Vehicle creation failed. Duplicate registration number: {}", request.registrationNumber());
            throw new UniqueResourceException("A vehicle already exists with this registration number: " + request.registrationNumber());
        }

        Vehicle vehicle = vehicleMapper.toEntity(request);
        Vehicle saved = vehicleRepository.save(vehicle);
        VehicleResponse response = vehicleMapper.toResponse(saved);

        log.debug("Vehicle created successfully: {}", response);
        return response;
    }

    @Override
    public VehicleResponse update(Long id, UpdateVehicleRequest request) {
        log.info("Updating vehicle with id: {}", id);

        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Vehicle not found for update with id: {}", id);
                    return new ResourceNotFoundException("Vehicle not found: " + id);
                });

        if(!vehicle.getRegistrationNumber().equalsIgnoreCase(request.registrationNumber())){
            if(vehicleRepository.findByRegistrationNumber(request.registrationNumber()).isPresent()){
                log.error("Vehicle update failed. Duplicate registration number: {}", request.registrationNumber());
                throw new UniqueResourceException("A vehicle already exists with this registration number: " + request.registrationNumber());
            }
        }

        vehicleMapper.updateEntityFromDto(request, vehicle);
        VehicleResponse response = vehicleMapper.toResponse(vehicle);

        log.debug("Vehicle updated successfully: {}", response);
        return response;
    }

    @Override
    public void delete(Long id) {
        log.info("Deleting vehicle with id: {}", id);
        if(!vehicleRepository.existsById(id)){
            log.error("Vehicle not found for deletion with id: {}", id);
            throw new ResourceNotFoundException("Vehicle not found: " + id);
        }
        vehicleRepository.deleteById(id);
        log.debug("Vehicle deleted successfully with id: {}", id);
    }

    @Override
    public VehicleResponse getById(Long id) {
        log.info("Fetching vehicle by id: {}", id);
        VehicleResponse response = vehicleRepository.findById(id)
                .map(vehicleMapper::toResponse)
                .orElseThrow(() -> {
                    log.error("Vehicle not found with id: {}", id);
                    return new ResourceNotFoundException("Vehicle not found: " + id);
                });
        log.debug("Fetched vehicle: {}", response);
        return response;
    }

    @Override
    public Page<VehicleResponse> getAll(int page, int size) {
        log.info("Fetching all vehicles - page: {}, size: {}", page, size);
        Pageable pageable = PageRequest.of(page, size);
        Page<VehicleResponse> responsePage = vehicleRepository.findAll(pageable)
                .map(vehicleMapper::toResponse);
        log.debug("Fetched {} vehicles", responsePage.getContent().size());
        return responsePage;
    }

    @Override
    public Page<VehicleResponse> getByType(VehicleType type, int page, int size) {
        log.info("Fetching vehicles of type {} - page: {}, size: {}", type, page, size);
        Pageable pageable = PageRequest.of(page, size);
        Page<VehicleResponse> responsePage = vehicleRepository.findByType(type, pageable)
                .map(vehicleMapper::toResponse);
        log.debug("Fetched {} vehicles of type {}", responsePage.getContent().size(), type);
        return responsePage;
    }

    @Override
    public VehicleResponse getByRegistrationNumber(String registrationNumber) {
        log.info("Fetching vehicle by registration number: {}", registrationNumber);
        VehicleResponse response = vehicleRepository.findByRegistrationNumber(registrationNumber)
                .map(vehicleMapper::toResponse)
                .orElseThrow(() -> {
                    log.error("Vehicle not found with registration number: {}", registrationNumber);
                    return new ResourceNotFoundException("Vehicle not found: " + registrationNumber);
                });
        log.debug("Fetched vehicle: {}", response);
        return response;
    }
}
