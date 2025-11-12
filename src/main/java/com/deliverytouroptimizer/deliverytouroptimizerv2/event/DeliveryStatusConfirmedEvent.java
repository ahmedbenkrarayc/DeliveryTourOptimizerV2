package com.deliverytouroptimizer.deliverytouroptimizerv2.event;

import com.deliverytouroptimizer.deliverytouroptimizerv2.dto.request.deliveryhistory.CreateDeliveryHistoryRequest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class DeliveryStatusConfirmedEvent {
    private final CreateDeliveryHistoryRequest request;
}
