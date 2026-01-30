package com.twelvegg.aicc.mydatabase.call.dto;

import com.twelvegg.aicc.mydatabase.call.domain.Transcript;
import lombok.Builder;
import java.time.LocalDateTime;

@Builder
public record TranscriptDto(
        Long id,
        String speaker,
        String content,
        LocalDateTime timestamp,
        String isFinal) {
    public static TranscriptDto from(Transcript transcript) {
        return TranscriptDto.builder()
                .id(transcript.getId())
                .speaker(transcript.getSpeaker())
                .content(transcript.getContent())
                .timestamp(transcript.getTimestamp())
                .isFinal(transcript.getIsFinal())
                .build();
    }
}
