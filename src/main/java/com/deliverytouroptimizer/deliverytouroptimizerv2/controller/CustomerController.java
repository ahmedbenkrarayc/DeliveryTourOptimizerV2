package com.deliverytouroptimizer.deliverytouroptimizerv2.controller;

import com.deliverytouroptimizer.deliverytouroptimizerv2.dto.request.customer.CreateCustomerRequest;
import com.deliverytouroptimizer.deliverytouroptimizerv2.dto.request.customer.UpdateCustomerRequest;
import com.deliverytouroptimizer.deliverytouroptimizerv2.dto.response.customer.CustomerResponse;
import com.deliverytouroptimizer.deliverytouroptimizerv2.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customer")
@RequiredArgsConstructor
@Slf4j
public class CustomerController {
    private final CustomerService customerService;

    @GetMapping
    public ResponseEntity<Page<CustomerResponse>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        log.info("Fetching all customers - page: {}, size: {}", page, size);
        Page<CustomerResponse> responsePage = customerService.getAll(page, size);
        log.debug("Fetched {} customers", responsePage.getContent().size());
        return ResponseEntity.ok(responsePage);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponse> getById(@PathVariable Long id){
        log.info("Fetching customer by id: {}", id);
        CustomerResponse response = customerService.getById(id);
        log.debug("Fetched customer: {}", response);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<CustomerResponse> create(@Valid @RequestBody CreateCustomerRequest request){
        log.info("Creating customer with name: {}", request.fname() + " " + request.lname());
        CustomerResponse response = customerService.create(request);
        log.debug("Customer created successfully: {}", response);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerResponse> update(@PathVariable Long id, @Valid @RequestBody UpdateCustomerRequest request){
        log.info("Updating customer with id: {}", id);
        CustomerResponse response = customerService.update(id, request);
        log.debug("Customer updated successfully: {}", response);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        log.info("Deleting customer with id: {}", id);
        customerService.delete(id);
        log.debug("Customer deleted successfully with id: {}", id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<Page<CustomerResponse>> search(
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        log.info("Searching customers with search: {}, page: {}, size: {}", search, page, size);
        Page<CustomerResponse> responsePage = customerService.search(search, page, size);
        log.debug("Search returned {} customers", responsePage.getContent().size());
        return ResponseEntity.ok(responsePage);
    }
}
