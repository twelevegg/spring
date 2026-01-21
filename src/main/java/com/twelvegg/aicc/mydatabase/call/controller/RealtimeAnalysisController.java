package com.twelvegg.aicc.mydatabase.call.controller;

import com.twelvegg.aicc.mydatabase.call.dto.RealtimeAnalysisResponseDto;
import com.twelvegg.aicc.mydatabase.call.service.RealtimeAnalysisService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "실시간 분석 관리", description = "실시간 분석 정보 관리 API")
@RestController
@RequestMapping("/api/v1/realtime-analyses")
@RequiredArgsConstructor
public class RealtimeAnalysisController {

    private final RealtimeAnalysisService realtimeAnalysisService;

    @Operation(summary = "실시간 분석 단건 조회", description = "ID로 실시간 분석 정보를 조회합니다.")
    @GetMapping("/{id}")
    public ResponseEntity<RealtimeAnalysisResponseDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(realtimeAnalysisService.findById(id));
    }
}
