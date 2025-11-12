package com.deliverytouroptimizer.deliverytouroptimizerv2.service.impl;

import com.deliverytouroptimizer.deliverytouroptimizerv2.dto.request.deliveryhistory.CreateDeliveryHistoryRequest;
import com.deliverytouroptimizer.deliverytouroptimizerv2.dto.response.deliveryhistory.DeliveryHistoryResponse;
import com.deliverytouroptimizer.deliverytouroptimizerv2.exception.ResourceNotFoundException;
import com.deliverytouroptimizer.deliverytouroptimizerv2.mapper.DeliveryHistoryMapper;
import com.deliverytouroptimizer.deliverytouroptimizerv2.model.DeliveryHistory;
import com.deliverytouroptimizer.deliverytouroptimizerv2.repository.DeliveryHistoryRepository;
import com.deliverytouroptimizer.deliverytouroptimizerv2.service.DeliveryHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class DeliveryHistoryServiceImpl implements DeliveryHistoryService {
    private final DeliveryHistoryRepository deliveryHistoryRepository;
    private final DeliveryHistoryMapper deliveryHistoryMapper;

    @Override
    public DeliveryHistoryResponse create(CreateDeliveryHistoryRequest request) {
        DeliveryHistory history = deliveryHistoryMapper.toEntity(request);
        DeliveryHistory saved = deliveryHistoryRepository.save(history);
        return deliveryHistoryMapper.toResponse(saved);
    }

    @Override
    public void delete(Long id) {
        if(!deliveryHistoryRepository.existsById(id))
            throw new ResourceNotFoundException("Delivery History not found id: "+id);
        deliveryHistoryRepository.deleteById(id);
    }

    @Override
    public DeliveryHistoryResponse getHistoryByDeliveryId(Long id) {
        return deliveryHistoryRepository.findByDelivery_Id(id)
                .map(deliveryHistoryMapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Delivery History not found delivery id: "+id));
    }

    @Override
    public List<DeliveryHistoryResponse> getAll() {
        return deliveryHistoryRepository.findAll()
                .stream()
                .map(deliveryHistoryMapper::toResponse)
                .collect(Collectors.toList());
    }
}
