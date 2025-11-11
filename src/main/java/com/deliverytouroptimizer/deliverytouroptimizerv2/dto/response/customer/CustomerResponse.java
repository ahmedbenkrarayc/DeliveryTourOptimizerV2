package com.deliverytouroptimizer.deliverytouroptimizerv2.dto.response.customer;

public record CustomerResponse(
        Long id,
        String fname,
        String lname,
        String email,
        String phone
) {
}
