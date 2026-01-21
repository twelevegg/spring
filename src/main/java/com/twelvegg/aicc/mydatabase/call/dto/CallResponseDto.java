package com.twelvegg.aicc.mydatabase.call.dto;

import com.twelvegg.aicc.mydatabase.call.domain.Call;
import lombok.Builder;

@Builder
public record CallResponseDto(
        Long id,
        String phoneNumber,
        Long tenantId,
        Long customerId) {
    public static CallResponseDto from(Call entity) {
        return CallResponseDto.builder()
                .id(entity.getId())
                .phoneNumber(entity.getPhoneNumber())
                .tenantId(entity.getTenant() != null ? entity.getTenant().getId() : null)
                .customerId(entity.getCustomer() != null ? entity.getCustomer().getId() : null)
                .build();
    }
}
