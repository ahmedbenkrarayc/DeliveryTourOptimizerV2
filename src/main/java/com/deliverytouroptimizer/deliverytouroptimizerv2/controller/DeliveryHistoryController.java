package com.deliverytouroptimizer.deliverytouroptimizerv2.controller;

import com.deliverytouroptimizer.deliverytouroptimizerv2.dto.request.deliveryhistory.CreateDeliveryHistoryRequest;
import com.deliverytouroptimizer.deliverytouroptimizerv2.dto.response.deliveryhistory.DeliveryHistoryResponse;
import com.deliverytouroptimizer.deliverytouroptimizerv2.service.DeliveryHistoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/deliveryhistory")
@RequiredArgsConstructor
@Slf4j
public class DeliveryHistoryController {
    private final DeliveryHistoryService deliveryHistoryService;

    @PostMapping
    public ResponseEntity<DeliveryHistoryResponse> create(@Valid @RequestBody CreateDeliveryHistoryRequest request){
        log.info("Creating delivery history for deliveryId: {}", request.deliveryId());
        DeliveryHistoryResponse response = deliveryHistoryService.create(request);
        log.debug("Delivery history created successfully: {}", response);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<DeliveryHistoryResponse>> getAll(){
        log.info("Fetching all delivery histories");
        List<DeliveryHistoryResponse> histories = deliveryHistoryService.getAll();
        log.debug("Fetched {} delivery histories", histories.size());
        return ResponseEntity.ok(histories);
    }

    @GetMapping("/delivery/{id}")
    public ResponseEntity<DeliveryHistoryResponse> getByDeliveryId(@PathVariable Long id){
        log.info("Fetching delivery history for deliveryId: {}", id);
        DeliveryHistoryResponse response = deliveryHistoryService.getHistoryByDeliveryId(id);
        log.debug("Fetched delivery history: {}", response);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        log.info("Deleting delivery history with id: {}", id);
        deliveryHistoryService.delete(id);
        log.debug("Delivery history deleted successfully with id: {}", id);
        return ResponseEntity.noContent().build();
    }
}
