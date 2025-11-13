package com.deliverytouroptimizer.deliverytouroptimizerv2.service.impl;

import com.deliverytouroptimizer.deliverytouroptimizerv2.dto.request.deliveryhistory.CreateDeliveryHistoryRequest;
import com.deliverytouroptimizer.deliverytouroptimizerv2.dto.response.deliveryhistory.DeliveryHistoryResponse;
import com.deliverytouroptimizer.deliverytouroptimizerv2.exception.ResourceNotFoundException;
import com.deliverytouroptimizer.deliverytouroptimizerv2.mapper.DeliveryHistoryMapper;
import com.deliverytouroptimizer.deliverytouroptimizerv2.model.DeliveryHistory;
import com.deliverytouroptimizer.deliverytouroptimizerv2.repository.DeliveryHistoryRepository;
import com.deliverytouroptimizer.deliverytouroptimizerv2.repository.DeliveryRepository;
import com.deliverytouroptimizer.deliverytouroptimizerv2.service.DeliveryHistoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class DeliveryHistoryServiceImpl implements DeliveryHistoryService {
    private final DeliveryHistoryRepository deliveryHistoryRepository;
    private final DeliveryHistoryMapper deliveryHistoryMapper;
    private final DeliveryRepository deliveryRepository;

    @Override
    public DeliveryHistoryResponse create(CreateDeliveryHistoryRequest request) {
        log.info("Creating delivery history for deliveryId: {}", request.deliveryId());
        validateParentExistence(request.deliveryId());

        DeliveryHistory history = deliveryHistoryMapper.toEntity(request);
        DeliveryHistory saved = deliveryHistoryRepository.save(history);
        DeliveryHistoryResponse response = deliveryHistoryMapper.toResponse(saved);

        log.debug("Delivery history created successfully: {}", response);
        return response;
    }

    @Override
    public void delete(Long id) {
        log.info("Deleting delivery history with id: {}", id);
        if(!deliveryHistoryRepository.existsById(id)) {
            log.error("Delivery history not found with id: {}", id);
            throw new ResourceNotFoundException("Delivery History not found id: "+id);
        }
        deliveryHistoryRepository.deleteById(id);
        log.debug("Delivery history deleted successfully with id: {}", id);
    }

    @Override
    public DeliveryHistoryResponse getHistoryByDeliveryId(Long id) {
        log.info("Fetching delivery history for deliveryId: {}", id);
        DeliveryHistoryResponse response = deliveryHistoryRepository.findByDelivery_Id(id)
                .map(deliveryHistoryMapper::toResponse)
                .orElseThrow(() -> {
                    log.error("Delivery history not found for deliveryId: {}", id);
                    return new ResourceNotFoundException("Delivery History not found delivery id: "+id);
                });
        log.debug("Fetched delivery history: {}", response);
        return response;
    }

    @Override
    public List<DeliveryHistoryResponse> getAll() {
        log.info("Fetching all delivery histories");
        List<DeliveryHistoryResponse> histories = deliveryHistoryRepository.findAll()
                .stream()
                .map(deliveryHistoryMapper::toResponse)
                .collect(Collectors.toList());
        log.debug("Fetched {} delivery histories", histories.size());
        return histories;
    }

    private void validateParentExistence(Long deliveryId){
        if(!deliveryRepository.existsById(deliveryId)) {
            log.error("Delivery not found with id: {}", deliveryId);
            throw new ResourceNotFoundException("Delivery not found id : " + deliveryId);
        }
        log.debug("Parent delivery exists with id: {}", deliveryId);
    }
}
