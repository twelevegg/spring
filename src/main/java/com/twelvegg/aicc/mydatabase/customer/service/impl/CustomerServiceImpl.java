package com.twelvegg.aicc.mydatabase.customer.service.impl;

import com.twelvegg.aicc.exception.CustomException;
import com.twelvegg.aicc.exception.ErrorCode;
import com.twelvegg.aicc.mydatabase.customer.domain.Customer;
import com.twelvegg.aicc.mydatabase.customer.dto.CustomerResponseDto;
import com.twelvegg.aicc.mydatabase.customer.repository.CustomerRepository;
import com.twelvegg.aicc.mydatabase.customer.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    @Override
    public CustomerResponseDto findById(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.RESOURCE_NOT_FOUND));
        return CustomerResponseDto.from(customer);
    }

    @Override
    @Transactional
    public Customer save(Customer customer) {
        return customerRepository.save(customer);
    }
}
