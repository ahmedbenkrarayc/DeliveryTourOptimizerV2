package com.deliverytouroptimizer.deliverytouroptimizerv2.optimizer.algorithm;

import com.deliverytouroptimizer.deliverytouroptimizerv2.model.Delivery;
import com.deliverytouroptimizer.deliverytouroptimizerv2.model.Warehouse;
import com.deliverytouroptimizer.deliverytouroptimizerv2.optimizer.TourOptimizer;

import java.util.*;

public class ClarkeWrightOptimizer implements TourOptimizer {

    @Override
    public List<Delivery> optimize(Warehouse warehouse, List<Delivery> deliveries) {
        if (deliveries.isEmpty()) {
            return new ArrayList<>();
        }

        if (deliveries.size() == 1) {
            return new ArrayList<>(deliveries);
        }

        List<Saving> savings = calculateSavings(warehouse, deliveries);
        savings.sort((a, b) -> Double.compare(b.value, a.value));

        Map<Delivery, List<Delivery>> routes = new HashMap<>();
        for (Delivery d : deliveries) {
            List<Delivery> route = new ArrayList<>();
            route.add(d);
            routes.put(d, route);
        }

        for (Saving s : savings) {
            List<Delivery> route1 = routes.get(s.d1);
            List<Delivery> route2 = routes.get(s.d2);

            if (route1 == route2) continue;

            if (isEndpoint(route1, s.d1) && isEndpoint(route2, s.d2)) {
                merge(route1, s.d1, route2, s.d2);

                for (Delivery d : route1) {
                    routes.put(d, route1);
                }
            }
        }

        Set<List<Delivery>> uniqueRoutes = new HashSet<>(routes.values());
        List<Delivery> result = new ArrayList<>();
        for (List<Delivery> route : uniqueRoutes) {
            result.addAll(route);
        }

        return result;
    }

    private List<Saving> calculateSavings(Warehouse w, List<Delivery> deliveries) {
        List<Saving> savings = new ArrayList<>();

        for (int i = 0; i < deliveries.size(); i++) {
            for (int j = i + 1; j < deliveries.size(); j++) {
                Delivery d1 = deliveries.get(i);
                Delivery d2 = deliveries.get(j);

                double saving = dist(w, d1) + dist(w, d2) - dist(d1, d2);
                savings.add(new Saving(d1, d2, saving));
            }
        }
        return savings;
    }

    private boolean isEndpoint(List<Delivery> route, Delivery d) {
        return route.get(0) == d || route.get(route.size() - 1) == d;
    }

    private void merge(List<Delivery> r1, Delivery d1, List<Delivery> r2, Delivery d2) {
        boolean d1Last = r1.get(r1.size() - 1) == d1;
        boolean d2First = r2.get(0) == d2;

        if (d1Last && d2First) {
            r1.addAll(r2);
        } else if (r1.get(0) == d1 && r2.get(r2.size() - 1) == d2) {
            r1.addAll(0, r2);
        } else if (d1Last && r2.get(r2.size() - 1) == d2) {
            Collections.reverse(r2);
            r1.addAll(r2);
        } else {
            Collections.reverse(r1);
            r1.addAll(r2);
        }
    }

    private double dist(Warehouse w, Delivery d) {
        return dist(w.getLatitude(), w.getLongitude(), d.getLatitude(), d.getLongitude());
    }

    private double dist(Delivery d1, Delivery d2) {
        return dist(d1.getLatitude(), d1.getLongitude(), d2.getLatitude(), d2.getLongitude());
    }

    private double dist(double lat1, double lon1, double lat2, double lon2) {
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);

        return 6371 * 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
    }

    private static class Saving {
        Delivery d1, d2;
        double value;

        Saving(Delivery d1, Delivery d2, double value) {
            this.d1 = d1;
            this.d2 = d2;
            this.value = value;
        }
    }
}