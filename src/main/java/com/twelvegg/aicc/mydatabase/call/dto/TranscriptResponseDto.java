package com.twelvegg.aicc.mydatabase.call.dto;

import com.twelvegg.aicc.mydatabase.call.domain.Transcript;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record TranscriptResponseDto(
        Long id,
        Long callId,
        String speaker,
        String content,
        LocalDateTime timestamp,
        String isFinal) {
    public static TranscriptResponseDto from(Transcript entity) {
        return TranscriptResponseDto.builder()
                .id(entity.getId())
                .callId(entity.getCall() != null ? entity.getCall().getId() : null)
                .speaker(entity.getSpeaker())
                .content(entity.getContent())
                .timestamp(entity.getTimestamp())
                .isFinal(entity.getIsFinal())
                .build();
    }
}
