package com.deliverytouroptimizer.deliverytouroptimizerv2.controller;

import com.deliverytouroptimizer.deliverytouroptimizerv2.dto.request.vehicle.CreateVehicleRequest;
import com.deliverytouroptimizer.deliverytouroptimizerv2.dto.request.vehicle.UpdateVehicleRequest;
import com.deliverytouroptimizer.deliverytouroptimizerv2.dto.response.vehicle.VehicleResponse;
import com.deliverytouroptimizer.deliverytouroptimizerv2.model.enums.VehicleType;
import com.deliverytouroptimizer.deliverytouroptimizerv2.service.VehicleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/vehicle")
@RequiredArgsConstructor
@Slf4j
public class VehicleController {
    private final VehicleService vehicleService;

    @PostMapping
    public ResponseEntity<VehicleResponse> create(@Valid @RequestBody CreateVehicleRequest request){
        log.info("Creating vehicle with registration number: {}", request.registrationNumber());
        VehicleResponse response = vehicleService.create(request);
        log.debug("Vehicle created successfully: {}", response);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<Page<VehicleResponse>> getAll(
            @RequestParam(defaultValue="0") int page,
            @RequestParam(defaultValue="10") int size
    ) {
        log.info("Fetching all vehicles - page: {}, size: {}", page, size);
        Page<VehicleResponse> responsePage = vehicleService.getAll(page, size);
        log.debug("Fetched {} vehicles", responsePage.getContent().size());
        return ResponseEntity.ok(responsePage);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VehicleResponse> getById(@PathVariable Long id){
        log.info("Fetching vehicle by id: {}", id);
        VehicleResponse response = vehicleService.getById(id);
        log.debug("Fetched vehicle: {}", response);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<VehicleResponse> update(@PathVariable Long id, @Valid @RequestBody UpdateVehicleRequest request){
        log.info("Updating vehicle with id: {}", id);
        VehicleResponse response = vehicleService.update(id, request);
        log.debug("Vehicle updated successfully: {}", response);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<Page<VehicleResponse>> getByType(
            @PathVariable VehicleType type,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        log.info("Fetching vehicles of type {} - page: {}, size: {}", type, page, size);
        Page<VehicleResponse> responsePage = vehicleService.getByType(type, page, size);
        log.debug("Fetched {} vehicles of type {}", responsePage.getContent().size(), type);
        return ResponseEntity.ok(responsePage);
    }

    @GetMapping("/registrationnumber/{number}")
    public ResponseEntity<VehicleResponse> getByRegistrationNumber(@PathVariable String number){
        log.info("Fetching vehicle by registration number: {}", number);
        VehicleResponse response = vehicleService.getByRegistrationNumber(number);
        log.debug("Fetched vehicle: {}", response);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVehicle(@PathVariable Long id) {
        log.info("Deleting vehicle with id: {}", id);
        vehicleService.delete(id);
        log.debug("Vehicle deleted successfully with id: {}", id);
        return ResponseEntity.noContent().build();
    }
}
