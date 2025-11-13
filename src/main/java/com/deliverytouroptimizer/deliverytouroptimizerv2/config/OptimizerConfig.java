package com.deliverytouroptimizer.deliverytouroptimizerv2.config;

import com.deliverytouroptimizer.deliverytouroptimizerv2.optimizer.algorithm.AIOptimizer;
import com.deliverytouroptimizer.deliverytouroptimizerv2.optimizer.algorithm.ClarkeWrightOptimizer;
import com.deliverytouroptimizer.deliverytouroptimizerv2.optimizer.algorithm.NearestNeighborOptimizer;
import com.deliverytouroptimizer.deliverytouroptimizerv2.optimizer.TourOptimizer;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OptimizerConfig {
    @Value("${optimizer.type}")
    private String optimizerType;

    @Bean
    public TourOptimizer optimizer(ChatClient.Builder chatClientBuilder) {
        return switch (optimizerType.toLowerCase()) {
            case "clark" -> new ClarkeWrightOptimizer();
            case "nearest" -> new NearestNeighborOptimizer();
            case "ai" -> new AIOptimizer(chatClientBuilder.build());
            default -> throw new IllegalArgumentException("Unknown optimizer type: " + optimizerType);
        };
    }
}
