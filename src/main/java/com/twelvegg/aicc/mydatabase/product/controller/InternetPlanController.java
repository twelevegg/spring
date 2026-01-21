package com.twelvegg.aicc.mydatabase.product.controller;

import com.twelvegg.aicc.mydatabase.product.dto.InternetPlanResponseDto;
import com.twelvegg.aicc.mydatabase.product.service.InternetPlanService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "인터넷 요금제 관리", description = "인터넷 요금제 정보 관리 API")
@RestController
@RequestMapping("/api/v1/internet-plans")
@RequiredArgsConstructor
public class InternetPlanController {

    private final InternetPlanService internetPlanService;

    @Operation(summary = "인터넷 요금제 단건 조회", description = "ID로 인터넷 요금제 정보를 조회합니다.")
    @GetMapping("/{id}")
    public ResponseEntity<InternetPlanResponseDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(internetPlanService.findById(id));
    }
}
