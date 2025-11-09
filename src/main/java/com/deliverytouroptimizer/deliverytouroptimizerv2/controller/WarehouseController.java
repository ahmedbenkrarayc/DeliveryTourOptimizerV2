package com.deliverytouroptimizer.deliverytouroptimizerv2.controller;

import com.deliverytouroptimizer.deliverytouroptimizerv2.dto.request.warehouse.CreateWareHouseRequest;
import com.deliverytouroptimizer.deliverytouroptimizerv2.dto.request.warehouse.UpdateWareHouseRequest;
import com.deliverytouroptimizer.deliverytouroptimizerv2.dto.response.warehouse.WareHouseResponse;
import com.deliverytouroptimizer.deliverytouroptimizerv2.service.WarehouseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/warehouse")
@RequiredArgsConstructor
public class WarehouseController {
    private final WarehouseService wareHouseService;

    @PostMapping
    public ResponseEntity<WareHouseResponse> create(@Valid @RequestBody CreateWareHouseRequest request){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(wareHouseService.create(request));
    }

    @GetMapping
    public ResponseEntity<List<WareHouseResponse>> getAll() {
        return ResponseEntity.ok(wareHouseService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<WareHouseResponse> getById(@PathVariable Long id){
        return ResponseEntity.ok(wareHouseService.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<WareHouseResponse> update(@PathVariable Long id, @Valid @RequestBody UpdateWareHouseRequest request){
        return ResponseEntity.ok(wareHouseService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWarehouse(@PathVariable Long id) {
        wareHouseService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
