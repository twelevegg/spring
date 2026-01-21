package com.twelvegg.aicc.mydatabase.call.controller;

import com.twelvegg.aicc.mydatabase.call.dto.TranscriptResponseDto;
import com.twelvegg.aicc.mydatabase.call.service.TranscriptService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "녹취록 관리", description = "녹취록 정보 관리 API")
@RestController
@RequestMapping("/api/v1/transcripts")
@RequiredArgsConstructor
public class TranscriptController {

    private final TranscriptService transcriptService;

    @Operation(summary = "녹취록 단건 조회", description = "ID로 녹취록 정보를 조회합니다.")
    @GetMapping("/{id}")
    public ResponseEntity<TranscriptResponseDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(transcriptService.findById(id));
    }
}
