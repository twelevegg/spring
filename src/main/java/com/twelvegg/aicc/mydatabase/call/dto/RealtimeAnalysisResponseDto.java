package com.twelvegg.aicc.mydatabase.call.dto;

import com.twelvegg.aicc.mydatabase.call.domain.RealtimeAnalysis;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record RealtimeAnalysisResponseDto(
        Long id,
        Long callId,
        String emotion,
        String intent,
        Double confidence,
        LocalDateTime createdAt) {
    public static RealtimeAnalysisResponseDto from(RealtimeAnalysis entity) {
        return RealtimeAnalysisResponseDto.builder()
                .id(entity.getId())
                .callId(entity.getCall() != null ? entity.getCall().getId() : null)
                .emotion(entity.getEmotion())
                .intent(entity.getIntent())
                .confidence(entity.getConfidence())
                .createdAt(entity.getCreatedAt())
                .build();
    }
}
