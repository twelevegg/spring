package com.twelvegg.aicc.mydatabase.call.controller;

import com.twelvegg.aicc.mydatabase.call.dto.AbnormalCaseResponseDto;
import com.twelvegg.aicc.mydatabase.call.service.AbnormalCaseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "이상 사례 관리", description = "이상 사례 정보 관리 API")
@RestController
@RequestMapping("/api/v1/abnormal-cases")
@RequiredArgsConstructor
public class AbnormalCaseController {

    private final AbnormalCaseService abnormalCaseService;

    @Operation(summary = "이상 사례 단건 조회", description = "ID로 이상 사례 정보를 조회합니다.")
    @GetMapping("/{id}")
    public ResponseEntity<AbnormalCaseResponseDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(abnormalCaseService.findById(id));
    }
}
