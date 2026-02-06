package com.twelvegg.aicc.mydatabase.member.dto;

import com.twelvegg.aicc.mydatabase.member.domain.Member;
import java.time.LocalDate;

public record MemberNewHireResponseDto(
        Long id,
        String name,
        String departmentName,
        LocalDate hireDate) {
    public static MemberNewHireResponseDto from(Member member) {
        return new MemberNewHireResponseDto(
                member.getId(),
                member.getName(),
                member.getDepartment() != null ? member.getDepartment().getName() : null,
                member.getHireDate()
        );
    }
}
