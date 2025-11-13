package com.deliverytouroptimizer.deliverytouroptimizerv2.service.impl;

import com.deliverytouroptimizer.deliverytouroptimizerv2.dto.request.customer.CreateCustomerRequest;
import com.deliverytouroptimizer.deliverytouroptimizerv2.dto.request.customer.UpdateCustomerRequest;
import com.deliverytouroptimizer.deliverytouroptimizerv2.dto.response.customer.CustomerResponse;
import com.deliverytouroptimizer.deliverytouroptimizerv2.exception.ResourceNotFoundException;
import com.deliverytouroptimizer.deliverytouroptimizerv2.mapper.CustomerMapper;
import com.deliverytouroptimizer.deliverytouroptimizerv2.model.Customer;
import com.deliverytouroptimizer.deliverytouroptimizerv2.repository.CustomerRepository;
import com.deliverytouroptimizer.deliverytouroptimizerv2.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    @Override
    public CustomerResponse create(CreateCustomerRequest request) {
        Customer customer = customerMapper.toEntity(request);
        Customer saved = customerRepository.save(customer);
        return customerMapper.toResponse(saved);
    }

    @Override
    public CustomerResponse update(Long id, UpdateCustomerRequest request) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found id: "+id));
        customerMapper.updateEntityFromDto(request, customer);
        return customerMapper.toResponse(customer);
    }

    @Override
    public void delete(Long id) {
        if(!customerRepository.existsById(id))
            throw new ResourceNotFoundException("Customer not found id: "+id);
        customerRepository.deleteById(id);
    }

    @Override
    public CustomerResponse getById(Long id) {
        return customerRepository.findById(id)
                .map(customerMapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found id: "+id));
    }

    @Override
    public Page<CustomerResponse> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return customerRepository.findAll(pageable)
                .map(customerMapper::toResponse);
    }

    @Override
    public Page<CustomerResponse> search(String search, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return customerRepository.search(search, pageable)
                .map(customerMapper::toResponse);
    }
}
