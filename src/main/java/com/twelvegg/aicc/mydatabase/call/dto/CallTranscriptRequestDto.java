package com.twelvegg.aicc.mydatabase.call.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CallTranscriptRequestDto(
        String speaker,
        @JsonProperty("transcript") String transcript,
        @JsonProperty("turn_id") Integer turnId
) {
}
