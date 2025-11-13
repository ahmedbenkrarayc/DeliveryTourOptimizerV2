package com.deliverytouroptimizer.deliverytouroptimizerv2.service;

import com.deliverytouroptimizer.deliverytouroptimizerv2.dto.request.customer.CreateCustomerRequest;
import com.deliverytouroptimizer.deliverytouroptimizerv2.dto.request.customer.UpdateCustomerRequest;
import com.deliverytouroptimizer.deliverytouroptimizerv2.dto.response.customer.CustomerResponse;
import org.springframework.data.domain.Page;


public interface CustomerService {
    CustomerResponse create(CreateCustomerRequest request);
    CustomerResponse update(Long id, UpdateCustomerRequest request);
    void delete(Long id);
    CustomerResponse getById(Long id);
    Page<CustomerResponse> getAll(int page, int size);
    Page<CustomerResponse> search(String search, int page, int size);
}
