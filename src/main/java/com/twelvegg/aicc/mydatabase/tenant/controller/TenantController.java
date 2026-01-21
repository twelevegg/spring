package com.twelvegg.aicc.mydatabase.tenant.controller;

import com.twelvegg.aicc.mydatabase.tenant.dto.TenantResponseDto;
import com.twelvegg.aicc.mydatabase.tenant.service.TenantService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "테넌트 관리", description = "테넌트 정보 관리 API")
@RestController
@RequestMapping("/api/v1/tenants")
@RequiredArgsConstructor
public class TenantController {

    private final TenantService tenantService;

    @Operation(summary = "테넌트 단건 조회", description = "ID로 테넌트 정보를 조회합니다.")
    @GetMapping("/{id}")
    public ResponseEntity<TenantResponseDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(tenantService.findById(id));
    }
}
