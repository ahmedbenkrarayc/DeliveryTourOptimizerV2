package com.deliverytouroptimizer.deliverytouroptimizerv2.service;

import com.deliverytouroptimizer.deliverytouroptimizerv2.dto.request.delivery.CreateDeliveryRequest;
import com.deliverytouroptimizer.deliverytouroptimizerv2.dto.request.delivery.UpdateDeliveryRequest;
import com.deliverytouroptimizer.deliverytouroptimizerv2.dto.response.delivery.DeliveryResponse;

import java.util.List;

public interface DeliveryService {
    DeliveryResponse create(CreateDeliveryRequest request);
    DeliveryResponse update(Long id, UpdateDeliveryRequest request);
    void delete(Long id);
    DeliveryResponse getById(Long id);
    List<DeliveryResponse> getAll();
}
