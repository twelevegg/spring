package com.twelvegg.aicc.mydatabase.call.controller;

import com.twelvegg.aicc.mydatabase.call.dto.CallResponseDto;
import com.twelvegg.aicc.mydatabase.call.dto.TranscriptDto;
import com.twelvegg.aicc.mydatabase.call.dto.CallAnalysisResponseDto;
import com.twelvegg.aicc.mydatabase.call.dto.CallEndRequestDto;
import com.twelvegg.aicc.mydatabase.call.dto.CallDetailResponseDto;
import com.twelvegg.aicc.mydatabase.call.service.CallService;
import java.util.List;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "통화 관리", description = "통화 정보 관리 API")
@RestController
@RequestMapping("/api/v1/calls")
@RequiredArgsConstructor
public class CallController {

    private final CallService callService;

    @Operation(summary = "통화 단건 조회", description = "ID로 통화 정보를 조회합니다.")
    @GetMapping("/{id}")
    public ResponseEntity<CallResponseDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(callService.findById(id));
    }



    @Operation(summary = "통화 오디오 조회", description = "통화의 오디오 파일 경로를 조회합니다.")
    @GetMapping("/{id}/audio")
    public ResponseEntity<String> getAudioPath(@PathVariable Long id) {
        return ResponseEntity.ok(callService.getAudioPath(id));
    }

    @Operation(summary = "통화 스크립트 조회", description = "통화의 전체 스크립트를 조회합니다.")
    @GetMapping("/{id}/transcript")
    public ResponseEntity<List<TranscriptDto>> getTranscript(@PathVariable Long id) {
        return ResponseEntity.ok(callService.getTranscripts(id));
    }

    @Operation(summary = "통화 분석 결과 조회", description = "통화의 분석 결과(요약, 감정, 키워드 등)를 조회합니다.")
    @GetMapping("/{id}/analysis")
    public ResponseEntity<CallAnalysisResponseDto> getAnalysis(@PathVariable Long id) {
        return ResponseEntity.ok(callService.getAnalysis(id));
    }

    @Operation(summary = "통화 상세 조회", description = "통화의 고객 정보와 대화 로그를 조회합니다.")
    @GetMapping("/{id}/detail")
    public ResponseEntity<CallDetailResponseDto> getDetail(@PathVariable Long id) {
        return ResponseEntity.ok(callService.getDetail(id));
    }
}
