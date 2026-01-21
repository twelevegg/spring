package com.twelvegg.aicc.mydatabase.call.dto;

import com.twelvegg.aicc.mydatabase.call.domain.AbnormalCase;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record AbnormalCaseResponseDto(
        Long id,
        Long callId,
        String description,
        String resolution,
        LocalDateTime createdAt) {
    public static AbnormalCaseResponseDto from(AbnormalCase entity) {
        return AbnormalCaseResponseDto.builder()
                .id(entity.getId())
                .callId(entity.getCall() != null ? entity.getCall().getId() : null)
                .description(entity.getDescription())
                .resolution(entity.getResolution())
                .createdAt(entity.getCreatedAt())
                .build();
    }
}
