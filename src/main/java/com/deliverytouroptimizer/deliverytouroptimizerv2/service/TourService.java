package com.deliverytouroptimizer.deliverytouroptimizerv2.service;

import com.deliverytouroptimizer.deliverytouroptimizerv2.dto.request.tour.CreateTourRequest;
import com.deliverytouroptimizer.deliverytouroptimizerv2.dto.request.tour.UpdateTourRequest;
import com.deliverytouroptimizer.deliverytouroptimizerv2.dto.response.delivery.DeliveryResponse;
import com.deliverytouroptimizer.deliverytouroptimizerv2.dto.response.tour.TourResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface TourService {
    TourResponse create(CreateTourRequest request);
    TourResponse update(Long id, UpdateTourRequest request);
    void delete(Long id);
    TourResponse getById(Long id);
    Page<TourResponse> getAll(int page, int size);
    List<DeliveryResponse> optimizeTour(Long tourId);
}
