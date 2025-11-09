package com.deliverytouroptimizer.deliverytouroptimizerv2.optimizer;

import com.deliverytouroptimizer.deliverytouroptimizerv2.model.Delivery;
import com.deliverytouroptimizer.deliverytouroptimizerv2.model.Warehouse;

import java.util.List;

public interface TourOptimizer {
    List<Delivery> optimize(Warehouse warehouse, List<Delivery> deliveries);
}
