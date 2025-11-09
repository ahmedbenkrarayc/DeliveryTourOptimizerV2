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
import com.deliverytouroptimizer.deliverytouroptimizerv2.service.TourService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class TourServiceImpl implements TourService {
    private final TourRepository tourRepository;
    private final TourMapper tourMapper;
    private final DeliveryMapper deliveryMapper;
    private final TourOptimizer tourOptimizer;

    @Override
    public TourResponse create(CreateTourRequest request) {
        Tour tour = tourMapper.toEntity(request);
        Tour saved = tourRepository.save(tour);
        return tourMapper.toResponse(saved);
    }

    @Override
    public TourResponse update(Long id, UpdateTourRequest request) {
        Tour tour = tourRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tour not found: "+ id));

        tourMapper.updateEntityFromDto(request, tour);
        return tourMapper.toResponse(tour);
    }

    @Override
    public void delete(Long id) {
        if(!tourRepository.existsById(id))
           throw new ResourceNotFoundException("Tour not found: "+ id);
        tourRepository.deleteById(id);
    }

    @Override
    public TourResponse getById(Long id) {
        return tourRepository.findById(id)
                .map(tourMapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Tour not found: "+ id));
    }

    @Override
    public List<TourResponse> getAll() {
        return tourRepository.findAll()
                .stream()
                .map(tourMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<DeliveryResponse> optimizeTour(Long tourId) {
        Tour tour = tourRepository.findById(tourId)
                .orElseThrow(() -> new ResourceNotFoundException("Tour not found id: " + tourId));

        List<Delivery> deliveries = tour.getDeliveries();
        Warehouse warehouse = tour.getWarehouse();

        List<DeliveryResponse> optimized = tourOptimizer.optimize(warehouse, deliveries)
                .stream()
                .map(deliveryMapper::toResponse)
                .collect(Collectors.toList());

        return optimized;
    }

}
