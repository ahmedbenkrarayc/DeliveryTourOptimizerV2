package com.deliverytouroptimizer.deliverytouroptimizerv2.controller;

import com.deliverytouroptimizer.deliverytouroptimizerv2.dto.request.delivery.CreateDeliveryRequest;
import com.deliverytouroptimizer.deliverytouroptimizerv2.dto.request.delivery.UpdateDeliveryRequest;
import com.deliverytouroptimizer.deliverytouroptimizerv2.dto.response.delivery.DeliveryResponse;
import com.deliverytouroptimizer.deliverytouroptimizerv2.service.DeliveryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/delivery")
@RequiredArgsConstructor
@Slf4j
public class DeliveryController {
    private final DeliveryService deliveryService;

    @GetMapping
    public ResponseEntity<Page<DeliveryResponse>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        log.info("Fetching all deliveries - page: {}, size: {}", page, size);
        Page<DeliveryResponse> responsePage = deliveryService.getAll(page, size);
        log.debug("Fetched {} deliveries", responsePage.getContent().size());
        return ResponseEntity.ok(responsePage);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DeliveryResponse> getById(@PathVariable Long id){
        log.info("Fetching delivery by id: {}", id);
        DeliveryResponse response = deliveryService.getById(id);
        log.debug("Fetched delivery: {}", response);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<DeliveryResponse> create(@Valid @RequestBody CreateDeliveryRequest request){
        log.info("Creating delivery for tourId: {} and customerId: {}", request.tourId(), request.customerId());
        DeliveryResponse response = deliveryService.create(request);
        log.debug("Delivery created successfully: {}", response);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DeliveryResponse> update(@PathVariable Long id, @Valid @RequestBody UpdateDeliveryRequest request){
        log.info("Updating delivery with id: {}", id);
        DeliveryResponse response = deliveryService.update(id, request);
        log.debug("Delivery updated successfully: {}", response);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        log.info("Deleting delivery with id: {}", id);
        deliveryService.delete(id);
        log.debug("Delivery deleted successfully with id: {}", id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<Page<DeliveryResponse>> search(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) Long customerId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        log.info("Searching deliveries with search: {}, customerId: {}, page: {}, size: {}", search, customerId, page, size);
        Page<DeliveryResponse> responsePage = deliveryService.search(search, customerId, page, size);
        log.debug("Search returned {} deliveries", responsePage.getContent().size());
        return ResponseEntity.ok(responsePage);
    }
}
