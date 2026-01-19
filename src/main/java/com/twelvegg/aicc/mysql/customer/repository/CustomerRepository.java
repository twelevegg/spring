package com.twelvegg.aicc.mysql.customer.repository;

import com.twelvegg.aicc.mysql.customer.domain.Customer;
import java.util.Optional;

public interface CustomerRepository {
    Optional<Customer> findById(Long id);
}
