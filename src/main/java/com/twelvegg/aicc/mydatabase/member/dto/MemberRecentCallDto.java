package com.twelvegg.aicc.mydatabase.member.dto;

import lombok.Builder;
import java.time.LocalDateTime;

@Builder
public record MemberRecentCallDto(
        Long callId,
        LocalDateTime startTime,
        String summaryText) {
}
