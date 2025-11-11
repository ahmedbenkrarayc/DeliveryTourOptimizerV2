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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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
    public List<CustomerResponse> getAll() {
        return customerRepository.findAll()
                .stream()
                .map(customerMapper::toResponse)
                .collect(Collectors.toList());
    }
}
