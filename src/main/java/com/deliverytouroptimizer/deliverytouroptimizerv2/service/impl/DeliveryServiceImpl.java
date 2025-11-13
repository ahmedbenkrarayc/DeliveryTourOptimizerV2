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
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class DeliveryServiceImpl implements DeliveryService {
    private final DeliveryRepository deliveryRepository;
    private final TourRepository tourRepository;
    private final CustomerRepository customerRepository;
    private final DeliveryMapper deliveryMapper;
    private final ApplicationEventPublisher publisher;
    private final Validator validator;

    @Override
    public DeliveryResponse create(CreateDeliveryRequest request) {
        log.info("Creating delivery for tourId: {}, customerId: {}", request.tourId(), request.customerId());
        Tour tour = tourRepository.findById(request.tourId())
                .orElseThrow(() -> {
                    log.error("Tour not found with id: {}", request.tourId());
                    return new ResourceNotFoundException("Tour not found id: "+request.tourId());
                });
        validateParentExistence(request.tourId(), request.customerId());

        Delivery delivery = deliveryMapper.toEntity(request);
        VehicleCapacityValidator.validateVehicleCapacity(tour, delivery, false);

        Delivery saved = deliveryRepository.save(delivery);
        DeliveryResponse response = deliveryMapper.toResponse(saved);

        log.debug("Delivery created successfully: {}", response);
        return response;
    }

    @Override
    public DeliveryResponse update(Long id, UpdateDeliveryRequest request) {
        log.info("Updating delivery with id: {}", id);

        Tour tour = tourRepository.findById(request.tourId())
                .orElseThrow(() -> {
                    log.error("Tour not found with id: {}", request.tourId());
                    return new ResourceNotFoundException("Tour not found id: "+request.tourId());
                });

        Delivery delivery = deliveryRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Delivery not found with id: {}", id);
                    return new ResourceNotFoundException("Delivery not found id: "+id);
                });

        if(delivery.getStatus() != request.status() && request.status() == DeliveryStatus.DELIVERED){
            log.info("Publishing DeliveryStatusConfirmedEvent for delivery id: {}", id);
            CreateDeliveryHistoryRequest historyRequest =
                    new CreateDeliveryHistoryRequest(request.deliveryDate(), request.plannedTime(), LocalTime.now(), id);

            Set<ConstraintViolation<CreateDeliveryHistoryRequest>> violations = validator.validate(historyRequest);
            if (!violations.isEmpty()) {
                String msg = violations.stream()
                        .map(v -> v.getPropertyPath() + ": " + v.getMessage())
                        .collect(Collectors.joining(", "));
                log.error("Validation failed for delivery history: {}", msg);
                throw new IllegalArgumentException(msg);
            }

            publisher.publishEvent(new DeliveryStatusConfirmedEvent(historyRequest));
        }

        VehicleCapacityValidator.validateVehicleCapacity(tour, delivery, true);
        deliveryMapper.updateEntityFromDto(request, delivery);
        DeliveryResponse response = deliveryMapper.toResponse(delivery);

        log.debug("Delivery updated successfully: {}", response);
        return response;
    }

    @Override
    public void delete(Long id) {
        log.info("Deleting delivery with id: {}", id);
        if(!deliveryRepository.existsById(id)){
            log.error("Delivery not found for deletion with id: {}", id);
            throw new ResourceNotFoundException("Delivery not found id: "+id);
        }
        deliveryRepository.deleteById(id);
        log.debug("Delivery deleted successfully with id: {}", id);
    }

    @Override
    public DeliveryResponse getById(Long id) {
        log.info("Fetching delivery by id: {}", id);
        DeliveryResponse response = deliveryRepository.findById(id)
                .map(deliveryMapper::toResponse)
                .orElseThrow(() -> {
                    log.error("Delivery not found with id: {}", id);
                    return new ResourceNotFoundException("Delivery not found id: "+id);
                });
        log.debug("Fetched delivery: {}", response);
        return response;
    }

    @Override
    public Page<DeliveryResponse> getAll(int page, int size) {
        log.info("Fetching all deliveries - page: {}, size: {}", page, size);
        Pageable pageable = PageRequest.of(page, size);
        Page<DeliveryResponse> responsePage = deliveryRepository.findAll(pageable)
                .map(deliveryMapper::toResponse);
        log.debug("Fetched {} deliveries", responsePage.getContent().size());
        return responsePage;
    }

    @Override
    public Page<DeliveryResponse> search(String search, Long customerId, int page, int size) {
        log.info("Searching deliveries for customerId: {} with query: '{}' - page: {}, size: {}", customerId, search, page, size);
        Pageable pageable = PageRequest.of(page, size);
        Page<DeliveryResponse> responsePage = deliveryRepository.searchDeliveries(search, customerId ,pageable)
                .map(deliveryMapper::toResponse);
        log.debug("Search returned {} deliveries", responsePage.getContent().size());
        return responsePage;
    }

    private void validateParentExistence(Long tourId, Long customerId){
        if(!tourRepository.existsById(tourId)){
            log.error("Tour not found with id: {}", tourId);
            throw new ResourceNotFoundException("Tour not found id : " + tourId);
        }
        if(!customerRepository.existsById(customerId)){
            log.error("Customer not found with id: {}", customerId);
            throw new ResourceNotFoundException("Customer not found id : " + customerId);
        }
        log.debug("Parent entities exist - tourId: {}, customerId: {}", tourId, customerId);
    }
}
