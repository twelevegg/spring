package com.twelvegg.aicc.mydatabase.customer.service.impl;

import com.twelvegg.aicc.exception.CustomException;
import com.twelvegg.aicc.exception.ErrorCode;
import com.twelvegg.aicc.mydatabase.customer.domain.Customer;
import com.twelvegg.aicc.mydatabase.customer.dto.CustomerResponseDto;
import com.twelvegg.aicc.mydatabase.customer.dto.CustomerDetailResponseDto;
import com.twelvegg.aicc.mydatabase.product.dto.BundleProductResponseDto;
import com.twelvegg.aicc.mydatabase.product.dto.InternetPlanResponseDto;
import com.twelvegg.aicc.mydatabase.product.dto.IptvPlanResponseDto;
import com.twelvegg.aicc.mydatabase.product.dto.MobilePlanResponseDto;
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

        @Override
        public CustomerDetailResponseDto findByPhoneNumber(String phoneNumber, String tenantName) {
                Customer customer = customerRepository.findByPhoneNumberAndTenant_Name(phoneNumber, tenantName)
                                .orElseThrow(() -> new CustomException(ErrorCode.RESOURCE_NOT_FOUND));

                return CustomerDetailResponseDto.builder()
                                .customer(CustomerResponseDto.from(customer))
                                .internetPlan(customer.getInternetPlan() != null
                                                ? InternetPlanResponseDto.from(customer.getInternetPlan())
                                                : null)
                                .mobilePlan(customer.getMobilePlan() != null
                                                ? MobilePlanResponseDto.from(customer.getMobilePlan())
                                                : null)
                                .iptvPlan(customer.getIptvPlan() != null
                                                ? IptvPlanResponseDto.from(customer.getIptvPlan())
                                                : null)
                                .bundleProduct(customer.getBundleProduct() != null
                                                ? BundleProductResponseDto.from(customer.getBundleProduct())
                                                : null)
                                .build();
        }
}
