package com.deliverytouroptimizer.deliverytouroptimizerv2.controller;

import com.deliverytouroptimizer.deliverytouroptimizerv2.dto.request.warehouse.CreateWareHouseRequest;
import com.deliverytouroptimizer.deliverytouroptimizerv2.dto.request.warehouse.UpdateWareHouseRequest;
import com.deliverytouroptimizer.deliverytouroptimizerv2.dto.response.warehouse.WareHouseResponse;
import com.deliverytouroptimizer.deliverytouroptimizerv2.service.WarehouseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/warehouse")
@RequiredArgsConstructor
@Slf4j
public class WarehouseController {
    private final WarehouseService wareHouseService;

    @PostMapping
    public ResponseEntity<WareHouseResponse> create(@Valid @RequestBody CreateWareHouseRequest request){
        log.info("Received request to create warehouse: {}", request);
        WareHouseResponse response = wareHouseService.create(request);
        log.debug("Warehouse created successfully: {}", response);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<Page<WareHouseResponse>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        log.info("Fetching all warehouses - page: {}, size: {}", page, size);
        Page<WareHouseResponse> warehouses = wareHouseService.getAll(page, size);
        log.debug("Fetched {} warehouses", warehouses.getContent().size());
        return ResponseEntity.ok(warehouses);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<WareHouseResponse>> search(
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        log.info("Searching warehouses with query: '{}' - page: {}, size: {}", search, page, size);
        Page<WareHouseResponse> result = wareHouseService.search(search, page, size);
        log.debug("Search returned {} warehouses", result.getContent().size());
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<WareHouseResponse> getById(@PathVariable Long id){
        log.info("Fetching warehouse by id: {}", id);
        WareHouseResponse response = wareHouseService.getById(id);
        log.debug("Fetched warehouse: {}", response);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<WareHouseResponse> update(@PathVariable Long id, @Valid @RequestBody UpdateWareHouseRequest request){
        log.info("Updating warehouse with id: {}", id);
        WareHouseResponse response = wareHouseService.update(id, request);
        log.debug("Warehouse updated successfully: {}", response);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWarehouse(@PathVariable Long id) {
        log.info("Deleting warehouse with id: {}", id);
        wareHouseService.delete(id);
        log.debug("Warehouse deleted successfully with id: {}", id);
        return ResponseEntity.noContent().build();
    }
}
