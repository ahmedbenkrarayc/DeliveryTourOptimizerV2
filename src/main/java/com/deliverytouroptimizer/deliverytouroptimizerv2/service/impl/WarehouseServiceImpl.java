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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class WarehouseServiceImpl implements WarehouseService {

    private final WarehouseRepository warehouseRepository;
    private final WarehouseMapper warehouseMapper;

    @Override
    public WareHouseResponse create(CreateWareHouseRequest request) {
        Warehouse warehouse = warehouseMapper.toEntity(request);
        Warehouse saved = warehouseRepository.save(warehouse);
        return warehouseMapper.toResponse(saved);
    }

    @Override
    public WareHouseResponse update(Long id, UpdateWareHouseRequest request) {
        Warehouse warehouse = warehouseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Warehouse not found: " + id));

        warehouseMapper.updateEntityFromDto(request, warehouse);

        Warehouse updated = warehouseRepository.save(warehouse);

        return warehouseMapper.toResponse(updated);
    }

    @Override
    public void delete(Long id) {
        if(!warehouseRepository.existsById(id)){
            throw new ResourceNotFoundException("Warehouse not found: " + id);
        }

        warehouseRepository.deleteById(id);
    }

    @Override
    public WareHouseResponse getById(Long id) {
        return warehouseRepository.findById(id)
                .map(warehouseMapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Warehouse not found: " + id));
    }

    @Override
    public List<WareHouseResponse> getAll() {
        return warehouseRepository.findAll()
                .stream()
                .map(warehouseMapper::toResponse)
                .collect(Collectors.toList());
    }
}
