package com.twelvegg.aicc.mydatabase.member.dto;

import com.twelvegg.aicc.mydatabase.member.domain.Member;

public record MemberSummaryResponseDto(
        Long id,
        String name,
        String status,
        Long metric) {
    public static MemberSummaryResponseDto of(Member member, Long metric) {
        return new MemberSummaryResponseDto(
                member.getId(),
                member.getName(),
                member.getStatus(),
                metric
        );
    }
}
