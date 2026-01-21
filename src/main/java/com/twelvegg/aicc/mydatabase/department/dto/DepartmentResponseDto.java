package com.twelvegg.aicc.mydatabase.department.dto;

import com.twelvegg.aicc.mydatabase.department.domain.Department;
import lombok.Builder;

@Builder
public record DepartmentResponseDto(
        Long id,
        Long tenantId,
        String name,
        String description) {
    public static DepartmentResponseDto from(Department entity) {
        return DepartmentResponseDto.builder()
                .id(entity.getId())
                .tenantId(entity.getTenant() != null ? entity.getTenant().getId() : null)
                .name(entity.getName())
                .description(entity.getDescription())
                .build();
    }
}
