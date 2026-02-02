package com.twelvegg.aicc.mydatabase.customer.controller;

import com.twelvegg.aicc.mydatabase.customer.dto.CustomerResponseDto;
import com.twelvegg.aicc.mydatabase.customer.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import com.twelvegg.aicc.mydatabase.customer.dto.CustomerDetailResponseDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "고객 관리", description = "고객 정보 관리 API")
@RestController
@RequestMapping("/ai/api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @Operation(summary = "고객 단건 조회", description = "ID로 고객 정보를 조회합니다.")
    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponseDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(customerService.findById(id));
    }

    @Operation(summary = "고객 상세 조회 (전화번호)", description = "전화번호와 테넌트 정보로 고객 및 상품 정보를 조회합니다.")
    @GetMapping("/search")
    public ResponseEntity<CustomerDetailResponseDto> findByPhoneNumber(
            @RequestParam String phoneNumber,
            @RequestAttribute("tenantName") String tenantName) {
        return ResponseEntity.ok(customerService.findByPhoneNumber(phoneNumber, tenantName));
    }
}
