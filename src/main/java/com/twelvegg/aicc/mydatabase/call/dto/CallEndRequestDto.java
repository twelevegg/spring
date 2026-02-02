package com.twelvegg.aicc.mydatabase.call.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class CallEndRequestDto{
    @JsonProperty("summary_text")
    private String summaryText;

    @JsonProperty("estimated_cost")
    private BigDecimal estimatedCost;

    @JsonProperty("ces_score")
    private Double cesScore;

    @JsonProperty("csat_score")
    private Double csatScore;

    @JsonProperty("rps_score")
    private Double npsScore;

    private List<String> keyword;

    @JsonProperty("violence_count")
    private Integer violenceCount;

    @JsonProperty("customer_number")
    private String customerNumber;

    private List<Map<String, String>> transcripts;
}