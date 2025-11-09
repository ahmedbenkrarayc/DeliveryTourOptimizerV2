package com.deliverytouroptimizer.deliverytouroptimizerv2.optimizer.algorithm;

import com.deliverytouroptimizer.deliverytouroptimizerv2.model.Delivery;
import com.deliverytouroptimizer.deliverytouroptimizerv2.model.Warehouse;
import com.deliverytouroptimizer.deliverytouroptimizerv2.optimizer.TourOptimizer;

import java.util.ArrayList;
import java.util.List;

public class NearestNeighborOptimizer implements TourOptimizer {

    @Override
    public List<Delivery> optimize(Warehouse warehouse, List<Delivery> deliveries) {
        if (deliveries.isEmpty()) {
            return new ArrayList<>();
        }

        List<Delivery> route = new ArrayList<>();
        List<Delivery> remaining = new ArrayList<>(deliveries);

        double lat = warehouse.getLatitude();
        double lon = warehouse.getLongitude();

        while (!remaining.isEmpty()) {
            Delivery nearest = findNearest(lat, lon, remaining);

            route.add(nearest);
            remaining.remove(nearest);

            lat = nearest.getLatitude();
            lon = nearest.getLongitude();
        }

        return route;
    }

    private Delivery findNearest(double lat, double lon, List<Delivery> deliveries) {
        Delivery nearest = null;
        double minDist = Double.MAX_VALUE;

        for (Delivery d : deliveries) {
            double dist = distance(lat, lon, d.getLatitude(), d.getLongitude());
            if (dist < minDist) {
                minDist = dist;
                nearest = d;
            }
        }
        return nearest;
    }

    private double distance(double lat1, double lon1, double lat2, double lon2) {
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return 6371 * c;
    }
}