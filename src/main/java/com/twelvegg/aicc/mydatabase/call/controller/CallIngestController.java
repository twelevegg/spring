package com.twelvegg.aicc.mydatabase.call.controller;

import com.twelvegg.aicc.mydatabase.call.dto.CallEndRequestDto;
import com.twelvegg.aicc.mydatabase.call.dto.CallEndResponseDto;
import com.twelvegg.aicc.mydatabase.call.service.CallIngestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "AI 통화 저장", description = "AI 요약/전문 저장 API")
@RestController
@RequestMapping("/ai/api/v1/calls")
@RequiredArgsConstructor
public class CallIngestController {

    private final CallIngestService callIngestService;

    @Operation(summary = "통화 종료 데이터 저장", description = "FastAPI 요약/전문 데이터를 저장합니다.")
    @PostMapping("/end")
    public ResponseEntity<CallEndResponseDto> saveCallEnd(@RequestBody CallEndRequestDto request) {
        return ResponseEntity.ok(callIngestService.saveCallEnd(request));
    }
}
