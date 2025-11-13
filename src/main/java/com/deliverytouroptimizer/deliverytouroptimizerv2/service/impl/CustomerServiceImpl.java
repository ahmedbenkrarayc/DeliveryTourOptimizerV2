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
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    @Override
    public CustomerResponse create(CreateCustomerRequest request) {
        log.info("Creating customer with name: {}", request.fname() + " " + request.lname());
        Customer customer = customerMapper.toEntity(request);
        Customer saved = customerRepository.save(customer);
        CustomerResponse response = customerMapper.toResponse(saved);
        log.debug("Customer created successfully: {}", response);
        return response;
    }

    @Override
    public CustomerResponse update(Long id, UpdateCustomerRequest request) {
        log.info("Updating customer with id: {}", id);
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Customer not found with id: {}", id);
                    return new ResourceNotFoundException("Customer not found id: "+id);
                });
        customerMapper.updateEntityFromDto(request, customer);
        CustomerResponse response = customerMapper.toResponse(customer);
        log.debug("Customer updated successfully: {}", response);
        return response;
    }

    @Override
    public void delete(Long id) {
        log.info("Deleting customer with id: {}", id);
        if(!customerRepository.existsById(id)){
            log.error("Customer not found for deletion with id: {}", id);
            throw new ResourceNotFoundException("Customer not found id: "+id);
        }
        customerRepository.deleteById(id);
        log.debug("Customer deleted successfully with id: {}", id);
    }

    @Override
    public CustomerResponse getById(Long id) {
        log.info("Fetching customer by id: {}", id);
        CustomerResponse response = customerRepository.findById(id)
                .map(customerMapper::toResponse)
                .orElseThrow(() -> {
                    log.error("Customer not found with id: {}", id);
                    return new ResourceNotFoundException("Customer not found id: "+id);
                });
        log.debug("Fetched customer: {}", response);
        return response;
    }

    @Override
    public Page<CustomerResponse> getAll(int page, int size) {
        log.info("Fetching all customers - page: {}, size: {}", page, size);
        Pageable pageable = PageRequest.of(page, size);
        Page<CustomerResponse> responsePage = customerRepository.findAll(pageable)
                .map(customerMapper::toResponse);
        log.debug("Fetched {} customers", responsePage.getContent().size());
        return responsePage;
    }

    @Override
    public Page<CustomerResponse> search(String search, int page, int size) {
        log.info("Searching customers with query: '{}' - page: {}, size: {}", search, page, size);
        Pageable pageable = PageRequest.of(page, size);
        Page<CustomerResponse> responsePage = customerRepository.search(search, pageable)
                .map(customerMapper::toResponse);
        log.debug("Search returned {} customers", responsePage.getContent().size());
        return responsePage;
    }
}
