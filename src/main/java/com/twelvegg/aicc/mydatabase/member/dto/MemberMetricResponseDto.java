package com.twelvegg.aicc.mydatabase.member.dto;

import com.twelvegg.aicc.mydatabase.member.domain.MemberMetric;
import lombok.Builder;

@Builder
public record MemberMetricResponseDto(
        Long id,
        Long memberId,
        Integer callCount,
        Double stressScore,
        Double burnoutRisk) {
    public static MemberMetricResponseDto from(MemberMetric entity) {
        return MemberMetricResponseDto.builder()
                .id(entity.getId())
                .memberId(entity.getMember() != null ? entity.getMember().getId() : null)
                .callCount(entity.getCallCount())
                .stressScore(entity.getStressScore())
                .burnoutRisk(entity.getBurnoutRisk())
                .build();
    }
}
