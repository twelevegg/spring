package com.twelvegg.aicc.mydatabase.tenant.dto;

import com.twelvegg.aicc.mydatabase.tenant.domain.Tenant;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record TenantResponseDto(
        Long id,
        String name,
        String status,
        LocalDateTime createdAt) {
    public static TenantResponseDto from(Tenant entity) {
        return TenantResponseDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .status(entity.getStatus())
                .createdAt(entity.getCreatedAt())
                .build();
    }
}
