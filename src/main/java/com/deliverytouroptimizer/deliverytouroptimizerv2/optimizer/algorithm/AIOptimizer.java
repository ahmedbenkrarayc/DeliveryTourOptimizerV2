package com.deliverytouroptimizer.deliverytouroptimizerv2.optimizer.algorithm;

import com.deliverytouroptimizer.deliverytouroptimizerv2.model.Delivery;
import com.deliverytouroptimizer.deliverytouroptimizerv2.model.Warehouse;
import com.deliverytouroptimizer.deliverytouroptimizerv2.optimizer.TourOptimizer;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class AIOptimizer implements TourOptimizer {
    private final ChatClient chatClient;

    @Override
    public List<Delivery> optimize(Warehouse warehouse, List<Delivery> deliveries) {
        if (deliveries.isEmpty()) return new ArrayList<>();
        if (deliveries.size() == 1) return new ArrayList<>(deliveries);

        String prompt = buildPrompt(warehouse, deliveries);
        String response = chatClient.prompt().user(prompt).call().content();

        return reorderDeliveries(response, deliveries);
    }

    private String buildPrompt(Warehouse warehouse, List<Delivery> deliveries) {
        StringBuilder sb = new StringBuilder();
        sb.append("Optimize this delivery route. Warehouse: ")
                .append(warehouse.getLatitude()).append(",").append(warehouse.getLongitude())
                .append("\n\nDeliveries:\n");

        for (Delivery d : deliveries) {
            sb.append(d.getId()).append(": ")
                    .append(d.getLatitude()).append(",").append(d.getLongitude()).append("\n");
        }

        sb.append("\nReturn only delivery IDs in optimal order, comma-separated. Example: 3,1,5,2");
        return sb.toString();
    }

    private List<Delivery> reorderDeliveries(String response, List<Delivery> deliveries) {
        Set<Long> validIds = deliveries.stream()
                .map(Delivery::getId)
                .collect(Collectors.toSet());

        Set<Long> orderedIds = new LinkedHashSet<>();
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(response);

        while (matcher.find()) {
            long id = Long.parseLong(matcher.group());
            if (validIds.contains(id)) {
                orderedIds.add(id);
            }
        }

        Map<Long, Delivery> deliveryMap = deliveries.stream()
                .collect(Collectors.toMap(Delivery::getId, d -> d));

        List<Delivery> result = new ArrayList<>();
        for (Long id : orderedIds) {
            result.add(deliveryMap.get(id));
        }

        return result;
    }
}