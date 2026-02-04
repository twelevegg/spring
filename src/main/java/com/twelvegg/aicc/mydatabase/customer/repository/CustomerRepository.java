package com.twelvegg.aicc.mydatabase.customer.repository;

import com.twelvegg.aicc.mydatabase.customer.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByPhoneNumberAndTenant_Name(String phoneNumber, String tenantName);

    Optional<Customer> findByPhoneNumber(String phoneNumber);
}
