package com.deliverytouroptimizer.deliverytouroptimizerv2.service.validation;

import com.deliverytouroptimizer.deliverytouroptimizerv2.model.Delivery;
import com.deliverytouroptimizer.deliverytouroptimizerv2.model.Tour;

import java.util.Objects;
import java.util.function.Predicate;

public class VehicleCapacityValidator {
    private VehicleCapacityValidator() {}

    public static void validateVehicleCapacity(Tour tour, Delivery delivery, Boolean update) {
        if (tour.getVehicle() == null || tour.getDeliveries() == null) return;

//        double totalWeight = tour.getDeliveries().stream().mapToDouble(Delivery::getWeight).sum() + delivery.getWeight();
//        double totalVolume = tour.getDeliveries().stream().mapToDouble(Delivery::getVolume).sum() + delivery.getVolume();
//        int totalDeliveries = tour.getDeliveries().size() + 1;

        double totalWeight, totalVolume;
        int totalDeliveries;

        if(update){
            Predicate<Delivery> deliveriesExceptUpdated = d -> !Objects.equals(d.getId(), delivery.getId());
            totalWeight = tour.getDeliveries().stream()
                    .filter(deliveriesExceptUpdated)
                    .mapToDouble(Delivery::getWeight)
                    .sum() + delivery.getWeight();
            totalVolume = tour.getDeliveries().stream()
                    .filter(deliveriesExceptUpdated)
                    .mapToDouble(Delivery::getVolume)
                    .sum() + delivery.getVolume();
            totalDeliveries = tour.getDeliveries().size();
        }else{
            totalWeight = tour.getDeliveries().stream().mapToDouble(Delivery::getWeight).sum() + delivery.getWeight();
            totalVolume = tour.getDeliveries().stream().mapToDouble(Delivery::getVolume).sum() + delivery.getVolume();
            totalDeliveries = tour.getDeliveries().size() + 1;
        }

        double maxWeight;
        double maxVolume;
        int maxDeliveries;

        switch (tour.getVehicle().getType()) {
            case BIKE -> {
                maxWeight = 50.0;
                maxVolume = 0.5;
                maxDeliveries = 15;
            }
            case VAN -> {
                maxWeight = 1000.0;
                maxVolume = 8.0;
                maxDeliveries = 50;
            }
            case TRUCK -> {
                maxWeight = 5000.0;
                maxVolume = 40.0;
                maxDeliveries = 100;
            }
            default -> throw new IllegalArgumentException("Unknown vehicle type: " + tour.getVehicle().getType());
        }

        if (totalDeliveries > maxDeliveries) {
            throw new IllegalStateException("Too many deliveries for vehicle " + tour.getVehicle().getType()
                    + ": " + totalDeliveries + " > " + maxDeliveries);
        }
        if (totalWeight > maxWeight) {
            throw new IllegalStateException("Total weight exceeds vehicle capacity: " + totalWeight + " > " + maxWeight);
        }
        if (totalVolume > maxVolume) {
            throw new IllegalStateException("Total volume exceeds vehicle capacity: " + totalVolume + " > " + maxVolume);
        }
    }
}
