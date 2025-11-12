package com.deliverytouroptimizer.deliverytouroptimizerv2.controller;

import com.deliverytouroptimizer.deliverytouroptimizerv2.dto.request.deliveryhistory.CreateDeliveryHistoryRequest;
import com.deliverytouroptimizer.deliverytouroptimizerv2.dto.response.deliveryhistory.DeliveryHistoryResponse;
import com.deliverytouroptimizer.deliverytouroptimizerv2.service.DeliveryHistoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/deliveryhistory")
@RequiredArgsConstructor
public class DeliveryHistoryController {
    private final DeliveryHistoryService deliveryHistoryService;

    @PostMapping
    public ResponseEntity<DeliveryHistoryResponse> create(@Valid @RequestBody CreateDeliveryHistoryRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(deliveryHistoryService.create(request));
    }

    @GetMapping
    public ResponseEntity<List<DeliveryHistoryResponse>> getAll(){
        return ResponseEntity.ok(deliveryHistoryService.getAll());
    }

    @GetMapping("/delivery/{id}")
    public ResponseEntity<DeliveryHistoryResponse> getByDeliveryId(@PathVariable Long id){
        return ResponseEntity.ok(deliveryHistoryService.getHistoryByDeliveryId(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DeliveryHistoryResponse> delete(@PathVariable Long id){
        deliveryHistoryService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
