package com.twelvegg.aicc.mydatabase.customer.repository;

import com.twelvegg.aicc.mydatabase.customer.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
