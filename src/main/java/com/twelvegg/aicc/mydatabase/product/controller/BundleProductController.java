package com.twelvegg.aicc.mydatabase.product.controller;

import com.twelvegg.aicc.mydatabase.product.dto.BundleProductResponseDto;
import com.twelvegg.aicc.mydatabase.product.service.BundleProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "결합 상품 관리", description = "결합 상품 정보 관리 API")
@RestController
@RequestMapping("/api/v1/bundle-products")
@RequiredArgsConstructor
public class BundleProductController {

    private final BundleProductService bundleProductService;

    @Operation(summary = "결합 상품 단건 조회", description = "ID로 결합 상품 정보를 조회합니다.")
    @GetMapping("/{id}")
    public ResponseEntity<BundleProductResponseDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(bundleProductService.findById(id));
    }
}
