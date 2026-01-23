package com.twelvegg.aicc.mydatabase.member.dto;

import com.twelvegg.aicc.mydatabase.member.domain.Member;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record MemberResponseDto(
        Long id,
        Long tenantId,
        Long departmentId,
        String name,
        String role,
        LocalDate hireDate,
        String email,
        String password,
        String status) {
    public static MemberResponseDto from(Member entity) {
        return MemberResponseDto.builder()
                .id(entity.getId())
                .tenantId(entity.getTenant() != null ? entity.getTenant().getId() : null)
                .departmentId(entity.getDepartment() != null ? entity.getDepartment().getId() : null)
                .name(entity.getName())
                .role(entity.getRole())
                .hireDate(entity.getHireDate())
                .email(entity.getEmail())
                .password(entity.getPassword())
                .status(entity.getStatus())
                .build();
    }
}
