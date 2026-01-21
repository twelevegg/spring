package com.twelvegg.aicc.mydatabase.call.dto;

import com.twelvegg.aicc.mydatabase.call.domain.PostCallSummary;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record PostCallSummaryResponseDto(
        Long id,
        Long callId,
        String summaryText,
        LocalDateTime createdAt) {
    public static PostCallSummaryResponseDto from(PostCallSummary entity) {
        return PostCallSummaryResponseDto.builder()
                .id(entity.getId())
                .callId(entity.getCall() != null ? entity.getCall().getId() : null)
                .summaryText(entity.getSummaryText())
                .createdAt(entity.getCreatedAt())
                .build();
    }
}
