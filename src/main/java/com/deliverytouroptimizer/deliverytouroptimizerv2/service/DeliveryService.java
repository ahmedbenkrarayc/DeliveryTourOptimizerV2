package com.deliverytouroptimizer.deliverytouroptimizerv2.service;

import com.deliverytouroptimizer.deliverytouroptimizerv2.dto.request.delivery.CreateDeliveryRequest;
import com.deliverytouroptimizer.deliverytouroptimizerv2.dto.request.delivery.UpdateDeliveryRequest;
import com.deliverytouroptimizer.deliverytouroptimizerv2.dto.response.delivery.DeliveryResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface DeliveryService {
    DeliveryResponse create(CreateDeliveryRequest request);
    DeliveryResponse update(Long id, UpdateDeliveryRequest request);
    void delete(Long id);
    DeliveryResponse getById(Long id);
    Page<DeliveryResponse> getAll(int page, int size);
    Page<DeliveryResponse> search(String search, Long customerId, int page, int size);
}
