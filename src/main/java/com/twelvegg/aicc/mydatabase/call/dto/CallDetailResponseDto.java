package com.twelvegg.aicc.mydatabase.call.dto;

import lombok.Builder;
import java.time.LocalDateTime;
import java.util.List;

@Builder
public record CallDetailResponseDto(
        Long id,
        LocalDateTime startTime,
        LocalDateTime endTime,
        Long duration,
        String customerName,
        String customerPhone,
        String audioPath,
        String summaryText,
        String keyword,
        List<TranscriptDto> transcripts) {
}
