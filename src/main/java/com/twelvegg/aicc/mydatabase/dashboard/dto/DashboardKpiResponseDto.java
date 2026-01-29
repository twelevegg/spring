package com.twelvegg.aicc.mydatabase.dashboard.dto;

import lombok.Builder;

@Builder
public record DashboardKpiResponseDto(
                SummaryKpi summary,
                CallPerformanceKpi callPerformance,
                OperationsKpi operations,
                AgentProductivityKpi agentProductivity) {

        @Builder
        public record SummaryKpi(
                        Double fcr,
                        Double nps,
                        Double ces,
                        Double csat,
                        Double sentimentScore) {
        }

        @Builder
        public record CallPerformanceKpi(
                        Double frt,
                        Double blockedCallRate,
                        Double abandonmentRate,
                        Integer activeWaitingCalls) {
        }

        @Builder
        public record OperationsKpi(
                        Long totalCallsProcessed,
                        Double cpc,
                        Double callArrivalRate,
                        String peakTimeTraffic,
                        Integer maxWaitTime,
                        Integer avgCallDuration,
                        Double avgResolutionTime,
                        Integer callbackRequests,
                        Double repeatCallRate,
                        Double selfServiceRate) {
        }

        @Builder
        public record AgentProductivityKpi(
                        Double attritionRate,
                        Double occupancyRate,
                        Double scheduleAdherence,
                        Double callsPerHour,
                        Double asa,
                        Double aht,
                        Double avgHoldTime,
                        Double transferRate,
                        Double avgAcwTime) {
        }
}
