package com.twelvegg.aicc.mydatabase.customer.service;

import com.twelvegg.aicc.mydatabase.customer.domain.Customer;
import com.twelvegg.aicc.mydatabase.customer.dto.CustomerResponseDto;

public interface CustomerService {
    CustomerResponseDto findById(Long id);

    Customer save(Customer customer);
}
