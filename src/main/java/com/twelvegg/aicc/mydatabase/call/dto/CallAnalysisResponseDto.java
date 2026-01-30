package com.twelvegg.aicc.mydatabase.call.dto;

import lombok.Builder;
import java.time.LocalDateTime;

@Builder
public record CallAnalysisResponseDto(
        Long callId,
        String summaryText,
        Double cesScore,
        Double csatScore,
        Double npsScore,
        Double sentimentScore,
        String keyword,
        LocalDateTime analyzedAt) {
}
