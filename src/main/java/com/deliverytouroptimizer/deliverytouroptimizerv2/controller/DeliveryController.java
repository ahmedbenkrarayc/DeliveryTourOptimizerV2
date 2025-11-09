package com.deliverytouroptimizer.deliverytouroptimizerv2.controller;

import com.deliverytouroptimizer.deliverytouroptimizerv2.dto.request.delivery.CreateDeliveryRequest;
import com.deliverytouroptimizer.deliverytouroptimizerv2.dto.request.delivery.UpdateDeliveryRequest;
import com.deliverytouroptimizer.deliverytouroptimizerv2.dto.response.delivery.DeliveryResponse;
import com.deliverytouroptimizer.deliverytouroptimizerv2.service.DeliveryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/delivery")
@RequiredArgsConstructor
public class DeliveryController {
    private final DeliveryService deliveryService;

    @GetMapping
    public ResponseEntity<List<DeliveryResponse>> getAll(){
        return ResponseEntity.ok(deliveryService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DeliveryResponse> getById(@PathVariable Long id){
        return ResponseEntity.ok(deliveryService.getById(id));
    }

    @PostMapping
    public ResponseEntity<DeliveryResponse> create(@Valid @RequestBody CreateDeliveryRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(deliveryService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DeliveryResponse> update(@PathVariable Long id, @Valid @RequestBody UpdateDeliveryRequest request){
        return ResponseEntity.ok(deliveryService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DeliveryResponse> delete(@PathVariable Long id){
        deliveryService.getById(id);
        return ResponseEntity.noContent().build();
    }
}
