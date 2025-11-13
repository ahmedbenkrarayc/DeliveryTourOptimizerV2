package com.deliverytouroptimizer.deliverytouroptimizerv2.service.impl;

import com.deliverytouroptimizer.deliverytouroptimizerv2.dto.request.tour.CreateTourRequest;
import com.deliverytouroptimizer.deliverytouroptimizerv2.dto.request.tour.UpdateTourRequest;
import com.deliverytouroptimizer.deliverytouroptimizerv2.dto.response.delivery.DeliveryResponse;
import com.deliverytouroptimizer.deliverytouroptimizerv2.dto.response.tour.TourResponse;
import com.deliverytouroptimizer.deliverytouroptimizerv2.exception.ResourceNotFoundException;
import com.deliverytouroptimizer.deliverytouroptimizerv2.mapper.DeliveryMapper;
import com.deliverytouroptimizer.deliverytouroptimizerv2.mapper.TourMapper;
import com.deliverytouroptimizer.deliverytouroptimizerv2.model.Delivery;
import com.deliverytouroptimizer.deliverytouroptimizerv2.model.Tour;
import com.deliverytouroptimizer.deliverytouroptimizerv2.model.Warehouse;
import com.deliverytouroptimizer.deliverytouroptimizerv2.optimizer.TourOptimizer;
import com.deliverytouroptimizer.deliverytouroptimizerv2.repository.TourRepository;
import com.deliverytouroptimizer.deliverytouroptimizerv2.repository.VehicleRepository;
import com.deliverytouroptimizer.deliverytouroptimizerv2.repository.WarehouseRepository;
import com.deliverytouroptimizer.deliverytouroptimizerv2.service.TourService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class TourServiceImpl implements TourService {
    private final TourRepository tourRepository;
    private final TourMapper tourMapper;
    private final DeliveryMapper deliveryMapper;
    private final TourOptimizer tourOptimizer;
    private final WarehouseRepository warehouseRepository;
    private final VehicleRepository vehicleRepository;

    @Override
    public TourResponse create(CreateTourRequest request) {
        log.info("Creating new tour for warehouseId: {}, vehicleId: {}", request.warehouseId(), request.vehicleId());
        validateParentExistence(request.warehouseId(), request.vehicleId());

        Tour tour = tourMapper.toEntity(request);
        Tour saved = tourRepository.save(tour);
        TourResponse response = tourMapper.toResponse(saved);

        log.debug("Tour created successfully: {}", response);
        return response;
    }

    @Override
    public TourResponse update(Long id, UpdateTourRequest request) {
        log.info("Updating tour with id: {}", id);
        Tour tour = tourRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Tour not found for update with id: {}", id);
                    return new ResourceNotFoundException("Tour not found: " + id);
                });

        validateParentExistence(request.warehouseId(), request.vehicleId());
        tourMapper.updateEntityFromDto(request, tour);
        TourResponse response = tourMapper.toResponse(tour);

        log.debug("Tour updated successfully: {}", response);
        return response;
    }

    @Override
    public void delete(Long id) {
        log.info("Deleting tour with id: {}", id);
        if(!tourRepository.existsById(id)){
            log.error("Tour not found for deletion with id: {}", id);
            throw new ResourceNotFoundException("Tour not found: " + id);
        }
        tourRepository.deleteById(id);
        log.debug("Tour deleted successfully with id: {}", id);
    }

    @Override
    public TourResponse getById(Long id) {
        log.info("Fetching tour by id: {}", id);
        TourResponse response = tourRepository.findById(id)
                .map(tourMapper::toResponse)
                .orElseThrow(() -> {
                    log.error("Tour not found with id: {}", id);
                    return new ResourceNotFoundException("Tour not found: " + id);
                });
        log.debug("Fetched tour: {}", response);
        return response;
    }

    @Override
    public Page<TourResponse> getAll(int page, int size) {
        log.info("Fetching all tours - page: {}, size: {}", page, size);
        Pageable pageable = PageRequest.of(page, size);
        Page<TourResponse> responsePage = tourRepository.findAll(pageable)
                .map(tourMapper::toResponse);
        log.debug("Fetched {} tours", responsePage.getContent().size());
        return responsePage;
    }

    @Override
    public List<DeliveryResponse> optimizeTour(Long tourId) {
        log.info("Optimizing tour with id: {}", tourId);
        Tour tour = tourRepository.findById(tourId)
                .orElseThrow(() -> {
                    log.error("Tour not found for optimization with id: {}", tourId);
                    return new ResourceNotFoundException("Tour not found id: " + tourId);
                });

        List<Delivery> deliveries = tour.getDeliveries();
        Warehouse warehouse = tour.getWarehouse();

        List<DeliveryResponse> optimized = tourOptimizer.optimize(warehouse, deliveries)
                .stream()
                .map(deliveryMapper::toResponse)
                .collect(Collectors.toList());

        log.debug("Tour optimized. {} deliveries in optimized tour", optimized.size());
        return optimized;
    }

    private void validateParentExistence(Long warehouseId, Long vehicleId){
        if(!warehouseRepository.existsById(warehouseId)){
            log.error("Warehouse not found with id: {}", warehouseId);
            throw new ResourceNotFoundException("Warehouse not found id : " + warehouseId);
        }
        if(!vehicleRepository.existsById(vehicleId)){
            log.error("Vehicle not found with id: {}", vehicleId);
            throw new ResourceNotFoundException("Vehicle not found id : " + vehicleId);
        }
        log.debug("Parent entities exist - warehouseId: {}, vehicleId: {}", warehouseId, vehicleId);
    }
}
