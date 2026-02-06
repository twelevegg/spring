package com.twelvegg.aicc.mydatabase.call.dto;

import lombok.Builder;

@Builder
public record CallEndResponseDto(
        Long callId,
        int transcriptCount,
        boolean summarySaved
) {
}
