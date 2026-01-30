package com.twelvegg.aicc.mydatabase.member.dto;

import lombok.Builder;
import java.util.List;

@Builder
public record MemberCallStatsDto(
        long todayCallCount,
        List<MemberRecentCallDto> recentCalls) {
}
