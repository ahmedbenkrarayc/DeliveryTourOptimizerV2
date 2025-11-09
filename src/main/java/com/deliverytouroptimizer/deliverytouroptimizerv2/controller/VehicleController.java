package com.deliverytouroptimizer.deliverytouroptimizerv2.controller;

import com.deliverytouroptimizer.deliverytouroptimizerv2.dto.request.vehicle.CreateVehicleRequest;
import com.deliverytouroptimizer.deliverytouroptimizerv2.dto.request.vehicle.UpdateVehicleRequest;
import com.deliverytouroptimizer.deliverytouroptimizerv2.dto.response.vehicle.VehicleResponse;
import com.deliverytouroptimizer.deliverytouroptimizerv2.model.enums.VehicleType;
import com.deliverytouroptimizer.deliverytouroptimizerv2.service.VehicleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vehicle")
@RequiredArgsConstructor
public class VehicleController {
    private final VehicleService vehicleService;

    @PostMapping
    public ResponseEntity<VehicleResponse> create(@Valid @RequestBody CreateVehicleRequest request){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(vehicleService.create(request));
    }

    @GetMapping
    public ResponseEntity<List<VehicleResponse>> getAll() {
        return ResponseEntity.ok(vehicleService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<VehicleResponse> getById(@PathVariable Long id){
        return ResponseEntity.ok(vehicleService.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<VehicleResponse> update(@PathVariable Long id, @Valid @RequestBody UpdateVehicleRequest request){
        return ResponseEntity.ok(vehicleService.update(id, request));
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<List<VehicleResponse>> getByType(@PathVariable VehicleType type){
        return ResponseEntity.ok(vehicleService.getByType(type));
    }

    @GetMapping("/registrationnumber/{number}")
    public ResponseEntity<VehicleResponse> getByRegistrationNumber(@PathVariable String number){
        return ResponseEntity.ok(vehicleService.getByRegistrationNumber(number));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVehicle(@PathVariable Long id) {
        vehicleService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
