package com.deliverytouroptimizer.deliverytouroptimizerv2.service;

import com.deliverytouroptimizer.deliverytouroptimizerv2.dto.request.deliveryhistory.CreateDeliveryHistoryRequest;
import com.deliverytouroptimizer.deliverytouroptimizerv2.dto.response.deliveryhistory.DeliveryHistoryResponse;

import java.util.List;

public interface DeliveryHistoryService {
    DeliveryHistoryResponse create(CreateDeliveryHistoryRequest request);
    void delete(Long id);
    DeliveryHistoryResponse getHistoryByDeliveryId(Long id);
    List<DeliveryHistoryResponse> getAll();
}
