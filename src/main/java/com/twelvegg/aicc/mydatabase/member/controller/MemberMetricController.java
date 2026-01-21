package com.twelvegg.aicc.mydatabase.member.controller;

import com.twelvegg.aicc.mydatabase.member.dto.MemberMetricResponseDto;
import com.twelvegg.aicc.mydatabase.member.service.MemberMetricService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "상담원 지표", description = "상담원 지표 정보 관리 API")
@RestController
@RequestMapping("/api/v1/member-metrics")
@RequiredArgsConstructor
public class MemberMetricController {

    private final MemberMetricService memberMetricService;

    @Operation(summary = "상담원 지표 단건 조회", description = "ID로 상담원 지표 정보를 조회합니다.")
    @GetMapping("/{id}")
    public ResponseEntity<MemberMetricResponseDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(memberMetricService.findById(id));
    }
}
