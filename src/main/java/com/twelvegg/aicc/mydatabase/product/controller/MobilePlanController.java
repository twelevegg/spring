package com.twelvegg.aicc.mydatabase.product.controller;

import com.twelvegg.aicc.mydatabase.product.dto.MobilePlanResponseDto;
import com.twelvegg.aicc.mydatabase.product.service.MobilePlanService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "모바일 요금제 관리", description = "모바일 요금제 정보 관리 API")
@RestController
@RequestMapping("/api/v1/mobile-plans")
@RequiredArgsConstructor
public class MobilePlanController {

    private final MobilePlanService mobilePlanService;

    @Operation(summary = "모바일 요금제 단건 조회", description = "ID로 모바일 요금제 정보를 조회합니다.")
    @GetMapping("/{id}")
    public ResponseEntity<MobilePlanResponseDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(mobilePlanService.findById(id));
    }
}
