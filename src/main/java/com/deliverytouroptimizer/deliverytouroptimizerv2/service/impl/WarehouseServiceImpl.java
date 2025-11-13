package com.deliverytouroptimizer.deliverytouroptimizerv2.service.impl;

import com.deliverytouroptimizer.deliverytouroptimizerv2.dto.request.warehouse.CreateWareHouseRequest;
import com.deliverytouroptimizer.deliverytouroptimizerv2.dto.request.warehouse.UpdateWareHouseRequest;
import com.deliverytouroptimizer.deliverytouroptimizerv2.dto.response.warehouse.WareHouseResponse;
import com.deliverytouroptimizer.deliverytouroptimizerv2.exception.ResourceNotFoundException;
import com.deliverytouroptimizer.deliverytouroptimizerv2.mapper.WarehouseMapper;
import com.deliverytouroptimizer.deliverytouroptimizerv2.model.Warehouse;
import com.deliverytouroptimizer.deliverytouroptimizerv2.repository.WarehouseRepository;
import com.deliverytouroptimizer.deliverytouroptimizerv2.service.WarehouseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class WarehouseServiceImpl implements WarehouseService {

    private final WarehouseRepository warehouseRepository;
    private final WarehouseMapper warehouseMapper;

    @Override
    public WareHouseResponse create(CreateWareHouseRequest request) {
        log.info("Creating new warehouse with data: {}", request);
        Warehouse warehouse = warehouseMapper.toEntity(request);
        Warehouse saved = warehouseRepository.save(warehouse);
        WareHouseResponse response = warehouseMapper.toResponse(saved);
        log.debug("Warehouse created successfully: {}", response);
        return response;
    }

    @Override
    public WareHouseResponse update(Long id, UpdateWareHouseRequest request) {
        log.info("Updating warehouse with id: {}", id);
        Warehouse warehouse = warehouseRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Warehouse not found for update with id: {}", id);
                    return new ResourceNotFoundException("Warehouse not found: " + id);
                });

        warehouseMapper.updateEntityFromDto(request, warehouse);
        Warehouse updated = warehouseRepository.save(warehouse);
        WareHouseResponse response = warehouseMapper.toResponse(updated);
        log.debug("Warehouse updated successfully: {}", response);
        return response;
    }

    @Override
    public void delete(Long id) {
        log.info("Deleting warehouse with id: {}", id);
        if(!warehouseRepository.existsById(id)){
            log.error("Warehouse not found for delete with id: {}", id);
            throw new ResourceNotFoundException("Warehouse not found: " + id);
        }
        warehouseRepository.deleteById(id);
        log.debug("Warehouse deleted successfully with id: {}", id);
    }

    @Override
    public WareHouseResponse getById(Long id) {
        log.info("Fetching warehouse by id: {}", id);
        WareHouseResponse response = warehouseRepository.findById(id)
                .map(warehouseMapper::toResponse)
                .orElseThrow(() -> {
                    log.error("Warehouse not found with id: {}", id);
                    return new ResourceNotFoundException("Warehouse not found: " + id);
                });
        log.debug("Fetched warehouse: {}", response);
        return response;
    }

    @Override
    public Page<WareHouseResponse> getAll(int page, int size) {
        log.info("Fetching all warehouses - page: {}, size: {}", page, size);
        Pageable pageable = PageRequest.of(page, size);
        Page<Warehouse> warehousesPage = warehouseRepository.findAll(pageable);
        Page<WareHouseResponse> responsePage = warehousesPage.map(warehouseMapper::toResponse);
        log.debug("Fetched {} warehouses", responsePage.getContent().size());
        return responsePage;
    }

    @Override
    public Page<WareHouseResponse> search(String search, int page, int size) {
        log.info("Searching warehouses with query: '{}' - page: {}, size: {}", search, page, size);
        Pageable pageable = PageRequest.of(page, size);
        Page<WareHouseResponse> responsePage = warehouseRepository.searchWarehouses(search, pageable)
                .map(warehouseMapper::toResponse);
        log.debug("Search result count: {}", responsePage.getContent().size());
        return responsePage;
    }
}
