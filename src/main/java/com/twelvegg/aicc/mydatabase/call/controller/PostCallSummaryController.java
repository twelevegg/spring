package com.twelvegg.aicc.mydatabase.call.controller;

import com.twelvegg.aicc.mydatabase.call.dto.PostCallSummaryResponseDto;
import com.twelvegg.aicc.mydatabase.call.service.PostCallSummaryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "상담 요약 관리", description = "상담 요약 정보 관리 API")
@RestController
@RequestMapping("/api/v1/post-call-summaries")
@RequiredArgsConstructor
public class PostCallSummaryController {

    private final PostCallSummaryService postCallSummaryService;

    @Operation(summary = "상담 요약 단건 조회", description = "ID로 상담 요약 정보를 조회합니다.")
    @GetMapping("/{id}")
    public ResponseEntity<PostCallSummaryResponseDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(postCallSummaryService.findById(id));
    }
}
