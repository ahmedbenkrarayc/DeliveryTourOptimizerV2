package com.deliverytouroptimizer.deliverytouroptimizerv2.service.impl;

import com.deliverytouroptimizer.deliverytouroptimizerv2.dto.request.delivery.CreateDeliveryRequest;
import com.deliverytouroptimizer.deliverytouroptimizerv2.dto.request.delivery.UpdateDeliveryRequest;
import com.deliverytouroptimizer.deliverytouroptimizerv2.dto.response.delivery.DeliveryResponse;
import com.deliverytouroptimizer.deliverytouroptimizerv2.exception.ResourceNotFoundException;
import com.deliverytouroptimizer.deliverytouroptimizerv2.mapper.DeliveryMapper;
import com.deliverytouroptimizer.deliverytouroptimizerv2.model.Delivery;
import com.deliverytouroptimizer.deliverytouroptimizerv2.model.Tour;
import com.deliverytouroptimizer.deliverytouroptimizerv2.repository.DeliveryRepository;
import com.deliverytouroptimizer.deliverytouroptimizerv2.repository.TourRepository;
import com.deliverytouroptimizer.deliverytouroptimizerv2.service.DeliveryService;
import com.deliverytouroptimizer.deliverytouroptimizerv2.service.validation.VehicleCapacityValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class DeliveryServiceImpl implements DeliveryService {
    private final DeliveryRepository deliveryRepository;
    private final TourRepository tourRepository;
    private final DeliveryMapper deliveryMapper;

    @Override
    public DeliveryResponse create(CreateDeliveryRequest request) {
        Tour tour = tourRepository.findById(request.tourId())
                .orElseThrow(() -> new ResourceNotFoundException("Tour not found id: "+request.tourId()));
        Delivery delivery = deliveryMapper.toEntity(request);
        VehicleCapacityValidator.validateVehicleCapacity(tour, delivery, false);
        Delivery saved = deliveryRepository.save(delivery);
        return deliveryMapper.toResponse(saved);
    }

    @Override
    public DeliveryResponse update(Long id, UpdateDeliveryRequest request) {
        Tour tour = tourRepository.findById(request.tourId())
                .orElseThrow(() -> new ResourceNotFoundException("Tour not found id: "+request.tourId()));

        Delivery delivery = deliveryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Delivery not found id: "+id));

        VehicleCapacityValidator.validateVehicleCapacity(tour, delivery, true);
        deliveryMapper.updateEntityFromDto(request, delivery);
        return deliveryMapper.toResponse(delivery);
    }

    @Override
    public void delete(Long id) {
        if(!deliveryRepository.existsById(id))
            throw new ResourceNotFoundException("Delivery not found id: "+id);
        deliveryRepository.deleteById(id);
    }

    @Override
    public DeliveryResponse getById(Long id) {
        return deliveryRepository.findById(id)
                .map(deliveryMapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Delivery not found id: "+id));
    }

    @Override
    public List<DeliveryResponse> getAll() {
        return deliveryRepository.findAll()
                .stream()
                .map(deliveryMapper::toResponse)
                .collect(Collectors.toList());
    }
}
