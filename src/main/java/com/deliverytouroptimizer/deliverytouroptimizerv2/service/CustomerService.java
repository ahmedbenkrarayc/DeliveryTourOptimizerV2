package com.deliverytouroptimizer.deliverytouroptimizerv2.service;

import com.deliverytouroptimizer.deliverytouroptimizerv2.dto.request.customer.CreateCustomerRequest;
import com.deliverytouroptimizer.deliverytouroptimizerv2.dto.request.customer.UpdateCustomerRequest;
import com.deliverytouroptimizer.deliverytouroptimizerv2.dto.response.customer.CustomerResponse;

import java.util.List;

public interface CustomerService {
    CustomerResponse create(CreateCustomerRequest request);
    CustomerResponse update(Long id, UpdateCustomerRequest request);
    void delete(Long id);
    CustomerResponse getById(Long id);
    List<CustomerResponse> getAll();
}
