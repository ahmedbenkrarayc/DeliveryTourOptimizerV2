package com.deliverytouroptimizer.deliverytouroptimizerv2.controller;

import com.deliverytouroptimizer.deliverytouroptimizerv2.dto.request.tour.CreateTourRequest;
import com.deliverytouroptimizer.deliverytouroptimizerv2.dto.request.tour.UpdateTourRequest;
import com.deliverytouroptimizer.deliverytouroptimizerv2.dto.response.delivery.DeliveryResponse;
import com.deliverytouroptimizer.deliverytouroptimizerv2.dto.response.tour.TourResponse;
import com.deliverytouroptimizer.deliverytouroptimizerv2.service.TourService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tour")
@RequiredArgsConstructor
@Slf4j
public class TourController {
    private final TourService tourService;

    @GetMapping
    public ResponseEntity<Page<TourResponse>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        log.info("Fetching all tours - page: {}, size: {}", page, size);
        Page<TourResponse> responsePage = tourService.getAll(page, size);
        log.debug("Fetched {} tours", responsePage.getContent().size());
        return ResponseEntity.ok(responsePage);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TourResponse> getById(@PathVariable Long id){
        log.info("Fetching tour by id: {}", id);
        TourResponse response = tourService.getById(id);
        log.debug("Fetched tour: {}", response);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<TourResponse> create(@Valid @RequestBody CreateTourRequest request){
        log.info("Creating tour for warehouseId: {} and vehicleId: {}", request.warehouseId(), request.vehicleId());
        TourResponse response = tourService.create(request);
        log.debug("Tour created successfully: {}", response);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TourResponse> update(@PathVariable Long id, @Valid @RequestBody UpdateTourRequest request){
        log.info("Updating tour with id: {}", id);
        TourResponse response = tourService.update(id, request);
        log.debug("Tour updated successfully: {}", response);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        log.info("Deleting tour with id: {}", id);
        tourService.delete(id);
        log.debug("Tour deleted successfully with id: {}", id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/optimize/tour/{id}")
    public ResponseEntity<List<DeliveryResponse>> optimize(@PathVariable Long id){
        log.info("Optimizing tour with id: {}", id);
        List<DeliveryResponse> optimized = tourService.optimizeTour(id);
        log.debug("Tour optimized successfully, {} deliveries returned", optimized.size());
        return ResponseEntity.ok(optimized);
    }
}
