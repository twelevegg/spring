package com.twelvegg.aicc.mydatabase.call.dto;

import lombok.Builder;
import java.time.LocalDateTime;
import java.util.List;
import java.math.BigDecimal;

@Builder
public record CallDetailResponseDto(
        Long id,
        String phoneNumber,
        String callType,
        LocalDateTime startTime,
        LocalDateTime endTime,
        Long duration,
        Integer transferCount,
        BigDecimal estimatedCost,
        String customerName,
        Integer customerAge,
        String customerGender,
        String customerPhone,
        String audioPath,
        String summaryText,
        String keyword,
        List<TranscriptDto> transcripts) {
}
