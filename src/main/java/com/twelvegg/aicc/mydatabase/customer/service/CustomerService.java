package com.twelvegg.aicc.mydatabase.customer.service;

import com.twelvegg.aicc.mydatabase.customer.domain.Customer;
import com.twelvegg.aicc.mydatabase.customer.dto.CustomerResponseDto;
import com.twelvegg.aicc.mydatabase.customer.dto.CustomerDetailResponseDto;

public interface CustomerService {
    CustomerResponseDto findById(Long id);

    Customer save(Customer customer);

    CustomerDetailResponseDto findByPhoneNumber(String phoneNumber, String tenantName);
}
