package com.twelvegg.aicc.mydatabase.call.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public record CallEndRequestDto(
        @JsonProperty("customer_number") String customerNumber,
        @JsonProperty("member_id") Long memberId,
        @JsonProperty("tenant_name") String tenantName,
        @JsonProperty("transcripts") List<CallTranscriptRequestDto> transcripts,
        @JsonProperty("summary_text") String summaryText,
        @JsonProperty("estimated_cost") Integer estimatedCost,
        @JsonProperty("ces_score") Double cesScore,
        @JsonProperty("csat_score") Double csatScore,
        @JsonProperty("rps_score") Double rpsScore,
        @JsonProperty("keyword") List<String> keyword,
        @JsonProperty("violence_count") Integer violenceCount
) {
}
