package com.deliverytouroptimizer.deliverytouroptimizerv2.service.impl;

import com.deliverytouroptimizer.deliverytouroptimizerv2.dto.request.delivery.CreateDeliveryRequest;
import com.deliverytouroptimizer.deliverytouroptimizerv2.dto.request.delivery.UpdateDeliveryRequest;
import com.deliverytouroptimizer.deliverytouroptimizerv2.dto.request.deliveryhistory.CreateDeliveryHistoryRequest;
import com.deliverytouroptimizer.deliverytouroptimizerv2.dto.response.delivery.DeliveryResponse;
import com.deliverytouroptimizer.deliverytouroptimizerv2.event.DeliveryStatusConfirmedEvent;
import com.deliverytouroptimizer.deliverytouroptimizerv2.exception.ResourceNotFoundException;
import com.deliverytouroptimizer.deliverytouroptimizerv2.mapper.DeliveryMapper;
import com.deliverytouroptimizer.deliverytouroptimizerv2.model.Delivery;
import com.deliverytouroptimizer.deliverytouroptimizerv2.model.Tour;
import com.deliverytouroptimizer.deliverytouroptimizerv2.model.enums.DeliveryStatus;
import com.deliverytouroptimizer.deliverytouroptimizerv2.repository.CustomerRepository;
import com.deliverytouroptimizer.deliverytouroptimizerv2.repository.DeliveryRepository;
import com.deliverytouroptimizer.deliverytouroptimizerv2.repository.TourRepository;
import com.deliverytouroptimizer.deliverytouroptimizerv2.service.DeliveryService;
import com.deliverytouroptimizer.deliverytouroptimizerv2.service.validation.VehicleCapacityValidator;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class DeliveryServiceImpl implements DeliveryService {
    private final DeliveryRepository deliveryRepository;
    private final TourRepository tourRepository;
    private final CustomerRepository customerRepository;
    private final DeliveryMapper deliveryMapper;
    private final ApplicationEventPublisher publisher;
    private final Validator validator;

    @Override
    public DeliveryResponse create(CreateDeliveryRequest request) {
        Tour tour = tourRepository.findById(request.tourId())
                .orElseThrow(() -> new ResourceNotFoundException("Tour not found id: "+request.tourId()));
        validateParentExistence(request.tourId(), request.customerId());
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

        if(delivery.getStatus() != request.status() && request.status() == DeliveryStatus.DELIVERED){
            CreateDeliveryHistoryRequest historyRequest =
                    new CreateDeliveryHistoryRequest(request.deliveryDate(), request.plannedTime(), LocalTime.now(), id);

            Set<ConstraintViolation<CreateDeliveryHistoryRequest>> violations = validator.validate(historyRequest);
            if (!violations.isEmpty()) {
                throw new IllegalArgumentException(
                        violations.stream()
                                .map(v -> v.getPropertyPath() + ": " + v.getMessage())
                                .collect(Collectors.joining(", "))
                );
            }

            publisher.publishEvent(new DeliveryStatusConfirmedEvent(historyRequest));
        }

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

    private void validateParentExistence(Long tourId, Long customerId){
        if(!tourRepository.existsById(tourId))
            throw new ResourceNotFoundException("Tour not found id : " + tourId);
        if(!customerRepository.existsById(customerId))
            throw new ResourceNotFoundException("Customer not found id : " + customerId);
    }
}
