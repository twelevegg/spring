package com.twelvegg.aicc.mydatabase.call.controller;

import com.twelvegg.aicc.mydatabase.call.dto.ViolenceEventResponseDto;
import com.twelvegg.aicc.mydatabase.call.service.ViolenceEventService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "폭언 탐지 관리", description = "폭언 탐지 정보 관리 API")
@RestController
@RequestMapping("/api/v1/violence-events")
@RequiredArgsConstructor
public class ViolenceEventController {

    private final ViolenceEventService violenceEventService;

    @Operation(summary = "폭언 탐지 단건 조회", description = "ID로 폭언 탐지 정보를 조회합니다.")
    @GetMapping("/{id}")
    public ResponseEntity<ViolenceEventResponseDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(violenceEventService.findById(id));
    }
}
