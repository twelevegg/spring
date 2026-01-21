package com.twelvegg.aicc.mydatabase.call.dto;

import com.twelvegg.aicc.mydatabase.call.domain.ViolenceEvent;
import lombok.Builder;

@Builder
public record ViolenceEventResponseDto(
        Long id,
        Long callId,
        Integer violenceCount) {
    public static ViolenceEventResponseDto from(ViolenceEvent entity) {
        return ViolenceEventResponseDto.builder()
                .id(entity.getId())
                .callId(entity.getCall() != null ? entity.getCall().getId() : null)
                .violenceCount(entity.getViolenceCount())
                .build();
    }
}
