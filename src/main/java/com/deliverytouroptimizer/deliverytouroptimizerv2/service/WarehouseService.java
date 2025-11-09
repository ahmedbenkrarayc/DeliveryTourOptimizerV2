package com.deliverytouroptimizer.deliverytouroptimizerv2.service;

import com.deliverytouroptimizer.deliverytouroptimizerv2.dto.request.warehouse.CreateWareHouseRequest;
import com.deliverytouroptimizer.deliverytouroptimizerv2.dto.request.warehouse.UpdateWareHouseRequest;
import com.deliverytouroptimizer.deliverytouroptimizerv2.dto.response.warehouse.WareHouseResponse;

import java.util.List;

public interface WarehouseService {
    WareHouseResponse create(CreateWareHouseRequest request);

    WareHouseResponse update(Long id, UpdateWareHouseRequest request);

    void delete(Long id);

    WareHouseResponse getById(Long id);

    List<WareHouseResponse> getAll();
}
