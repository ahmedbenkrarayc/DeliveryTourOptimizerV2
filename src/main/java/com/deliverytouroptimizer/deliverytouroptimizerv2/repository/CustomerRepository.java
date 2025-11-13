package com.deliverytouroptimizer.deliverytouroptimizerv2.repository;

import com.deliverytouroptimizer.deliverytouroptimizerv2.model.Customer;
import io.micrometer.common.lang.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    @NonNull
    Page<Customer> findAll(@NonNull Pageable pageable);

    @Query("""
        SELECT c FROM Customer c
        WHERE (:search IS NULL OR :search = ''
            OR LOWER(c.fname) LIKE CONCAT("%", :search, "%")
            OR LOWER(c.lname) LIKE CONCAT("%", :search, "%")
            OR LOWER(c.email) LIKE CONCAT("%", :search, "%")
            OR LOWER(c.phone) LIKE CONCAT("%", :search, "%"))
    """)
    Page<Customer> search(
            @Param("search") String search,
            Pageable pageable
    );
}
