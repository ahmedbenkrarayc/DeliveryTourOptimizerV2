package com.deliverytouroptimizer.deliverytouroptimizerv2.listener;

import com.deliverytouroptimizer.deliverytouroptimizerv2.event.DeliveryStatusConfirmedEvent;
import com.deliverytouroptimizer.deliverytouroptimizerv2.service.DeliveryHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeliveryStatusConfirmedListener {
    private final DeliveryHistoryService deliveryHistoryService;

    @EventListener
    public void handle(DeliveryStatusConfirmedEvent event){
        deliveryHistoryService.create(event.getRequest());
    }
}
