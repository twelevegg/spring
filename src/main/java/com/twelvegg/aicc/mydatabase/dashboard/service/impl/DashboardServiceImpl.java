package com.twelvegg.aicc.mydatabase.dashboard.service.impl;

import com.twelvegg.aicc.cdr.repository.CdrRepository;
import com.twelvegg.aicc.mydatabase.call.repository.CallRepository;
import com.twelvegg.aicc.mydatabase.call.repository.PostCallSummaryRepository;
import com.twelvegg.aicc.mydatabase.dashboard.dto.DashboardKpiResponseDto;
import com.twelvegg.aicc.mydatabase.dashboard.service.DashboardService;
import com.twelvegg.aicc.mydatabase.member.repository.MemberMetricRepository;
import com.twelvegg.aicc.mydatabase.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DashboardServiceImpl implements DashboardService {

    private final CdrRepository cdrRepository;
    private final CallRepository callRepository;
    private final PostCallSummaryRepository postCallSummaryRepository;
    private final MemberMetricRepository memberMetricRepository;
    private final MemberRepository memberRepository;

    @Override
    public DashboardKpiResponseDto getGlobalKpi() {
        return buildKpiResponse(false, null);
    }

    @Override
    public DashboardKpiResponseDto getMemberKpi(Long memberId) {
        return buildKpiResponse(true, memberId);
    }

    private DashboardKpiResponseDto buildKpiResponse(boolean isMemberSpecific, Long memberId) {
        // --------------------------------------------------------------------------------
        // 1. Summary KPIs
        // --------------------------------------------------------------------------------

        // 1. 최초 문의 해결률 (FCR)
        // - 수식: (첫 통화에서 해결된 건수 / 총 접수 건수) * 100 [단위: %]
        // - 여기서는 (전체 고객 수 - 재상담 고객 수) / 전체 고객 수 * 100 로 산출
        Double fcr = calculateFcr(isMemberSpecific, memberId);

        // 3. 고객 노력 점수 (CES)
        // - 수식: 응답 점수 총합 / 총 응답자 수 (보통 5점 또는 7점 척도 평균) [단위: 점수]
        Double ces = isMemberSpecific ? postCallSummaryRepository.findAvgCesScoreByMemberId(memberId)
                : postCallSummaryRepository.findAvgCesScore();

        // 4. 고객 만족도 (CSAT)
        // - 수식: (만족 응답 수(4~5점) / 총 응답 수) * 100 또는 응답 점수 평균 [단위: %/점수]
        Double csat = isMemberSpecific ? postCallSummaryRepository.findAvgCsatScoreByMemberId(memberId)
                : postCallSummaryRepository.findAvgCsatScore();

        // 2. 순추천 고객 지수 (NPS)
        // - 수식: 추천 고객 비율과 비추천 고객 비율 사이의 차이로 계산됩니다.
        Double nps = isMemberSpecific ? postCallSummaryRepository.findAvgNpsScoreByMemberId(memberId)
                : postCallSummaryRepository.findAvgNpsScore();

        // 5. 고객 감정 분석 점수
        // - 수식: AI 감정 점수(긍정~부정) 평균 [단위: 점수]
        Double sentiment = isMemberSpecific ? postCallSummaryRepository.findAvgSentimentScoreByMemberId(memberId)
                : postCallSummaryRepository.findAvgSentimentScore();

        DashboardKpiResponseDto.SummaryKpi summary = DashboardKpiResponseDto.SummaryKpi.builder()
                .fcr(formatDouble(fcr))
                .ces(formatDouble(ces))
                .csat(formatDouble(csat))
                .nps(formatDouble(nps))
                .sentimentScore(formatDouble(sentiment))
                .build();

        // --------------------------------------------------------------------------------
        // 2. Call Performance KPIs
        // --------------------------------------------------------------------------------

        long totalCalls = isMemberSpecific ? callRepository.countByMemberId(memberId) : cdrRepository.count();
        long answeredCalls = cdrRepository.countByAnswerIsNotNull(); // Global only (CDR)
        long busyCalls = cdrRepository.countByDisposition("BUSY");
        long failedCalls = cdrRepository.countByDisposition("FAILED");
        long noAnswerCalls = cdrRepository.countByDisposition("NO ANSWER");
        long abandonedCalls = failedCalls + noAnswerCalls;

        // 6. 최초 응답 시간 (FRT)
        // - 수식: 상담원 연결까지 대기한 시간의 총합 / 응답된 총 통화 수 [단위: 초/분]
        Double frt = cdrRepository.findAvgWaitTime();

        // 7. 통화 차단율 (Blocked Call Rate)
        // - 수식: (차단되거나 통화중 신호를 받은 통화 수 / 총 인입 통화 수) * 100 [단위: %]
        Double blockedRate = 0.0;

        // 8. 평균 통화 포기율 (Abandonment Rate)
        // - 수식: (연결 전 끊어진 통화 수 / 총 인입 통화 수) * 100 [단위: %]
        Double abandonRate = 0.0;

        // 9. 현재 활성 대기 통화 수
        // - 수식: 현재 시점(Now)에 callStatus='WAITING'인 건수 [단위: 건]
        Integer activeCalls = 0;
        if (isMemberSpecific) {
            activeCalls = (int) callRepository.countByMemberIdAndPostCallSummaryIsNull(memberId);
        } else {
            activeCalls = (int) cdrRepository.countActiveCalls();
        }

        if (!isMemberSpecific && cdrRepository.count() > 0) {
            blockedRate = (double) busyCalls / cdrRepository.count() * 100;
            abandonRate = (double) abandonedCalls / cdrRepository.count() * 100;
        }

        DashboardKpiResponseDto.CallPerformanceKpi callPerformance = DashboardKpiResponseDto.CallPerformanceKpi
                .builder()
                .frt(formatDouble(frt))
                .blockedCallRate(formatDouble(blockedRate))
                .abandonmentRate(formatDouble(abandonRate))
                .activeWaitingCalls(activeCalls)
                .build();

        // --------------------------------------------------------------------------------
        // 3. Operations KPIs
        // --------------------------------------------------------------------------------

        // 10. 처리된 총 통화 수
        // - 수식: 기간 내 callStatus='ANSWERED'인 통화 총합 [단위: 건]
        // (여기서는 Total Proccessed Calls로 사용)

        // 11. 통화당 비용 (CPC)
        // - 수식: 총 콜센터 운영 비용 / 처리된 총 통화 수 [단위: 원]
        BigDecimal totalCost = isMemberSpecific ? callRepository.sumEstimatedCostByMemberId(memberId)
                : callRepository.sumEstimatedCost();
        Double cpc = (totalCalls > 0 && totalCost != null)
                ? totalCost.divide(BigDecimal.valueOf(totalCalls), 2, RoundingMode.HALF_UP).doubleValue()
                : 0.0;

        // 12. 통화 착신율 (Call Arrival Rate)
        // - 수식: 단위 시간(예: 1시간) 동안 인입된 통화 수 합계 [단위: 건/시간]
        // (Daily Average logic used here: Total / 24)
        Double arrivalRate = totalCalls / 24.0;

        // 13. 피크 시간 트래픽
        // - 수식: 시간대별 인입 통화량 중 최대값 (Max(Hourly Call Volume)) [단위: 시각]
        List<Object[]> peakTraffic = cdrRepository.findPeakHourTraffic();
        String peakTime = "N/A";
        if (peakTraffic != null && !peakTraffic.isEmpty()) {
            Object[] row = peakTraffic.get(0);
            peakTime = row[0] + ":00 (" + row[1] + " calls)";
        }

        // 14. 최장 대기 시간
        // - 수식: Max(connectedAt - startedAt) (대기 중인 통화 중 가장 오래된 값) [단위: 초]
        Integer maxWait = cdrRepository.findMaxWaitTime();

        // 15. 평균 통화 길이
        // - 수식: 총 duration 합계 / 총 통화 건수 [단위: 초/분]
        Double avgDuration = cdrRepository.findAvgDuration();

        // 16. 평균 문의 처리 기간 -> Pending (Avg Resolution Time)
        // 17. 콜백 메시징 요청 수 -> Pending
        // 19. 채널 믹스 -> Pending
        // 20. 채널 봉쇄율 (Self-Service Rate) -> Pending

        // 18. 반복 통화율 (Repeat Call Rate)
        // - 수식: (동일 고객의 동일 사안 재인입 건수 / 총 처리 건수) * 100 [단위: %]
        DashboardKpiResponseDto.OperationsKpi operations = DashboardKpiResponseDto.OperationsKpi.builder()
                .totalCallsProcessed((long) totalCalls)
                .cpc(cpc)
                .callArrivalRate(formatDouble(arrivalRate))
                .peakTimeTraffic(peakTime)
                .maxWaitTime(maxWait != null ? maxWait : 0)
                .avgCallDuration(avgDuration != null ? avgDuration.intValue() : 0)
                .avgResolutionTime((avgDuration != null ? avgDuration : 0.0) * 1.2) // Estimate: Duration + 20% Wrap-up
                .callbackRequests(0)
                .repeatCallRate(calculateRepeatCallRate(isMemberSpecific, memberId, totalCalls))
                .selfServiceRate(calculateSelfServiceRate(isMemberSpecific, totalCalls))
                .build();

        // --------------------------------------------------------------------------------
        // 4. Agent Productivity
        // --------------------------------------------------------------------------------

        Long totalLogin = isMemberSpecific ? memberMetricRepository.sumTotalLoginTimeByMemberId(memberId)
                : memberMetricRepository.sumTotalLoginTime();
        Long totalTalk = isMemberSpecific ? memberMetricRepository.sumTotalTalkTimeByMemberId(memberId)
                : memberMetricRepository.sumTotalTalkTime();
        Long totalBreak = isMemberSpecific ? memberMetricRepository.sumTotalBreakTimeByMemberId(memberId)
                : memberMetricRepository.sumTotalBreakTime();

        // 23. 일정 준수율 (Schedule Adherence)
        // - 수식: (계획된 스케줄 준수 시간 / 총 스케줄 시간) * 100 [단위: %]
        Double adherence = isMemberSpecific ? memberMetricRepository.findAvgScheduleAdherenceByMemberId(memberId)
                : memberMetricRepository.findAvgScheduleAdherence();

        // 22. 상담원 활용률 (Occupancy Rate)
        // - 수식: ((통화 시간 + 후처리 시간) / (총 로그인 시간 - 휴식 시간)) * 100 [단위: %]
        Double occupancy = 0.0;
        if (totalLogin != null && totalLogin > 0 && totalTalk != null) {
            long effectiveWork = totalLogin - (totalBreak != null ? totalBreak : 0);
            if (effectiveWork > 0) {
                occupancy = (double) totalTalk / effectiveWork * 100;
            }
        }

        // 24. 시간당 응답 통화 수
        // - 수식: 총 처리 통화 수 / 총 상담원 근무 시간 [단위: 건/시간]
        Double callsPerHour = 0.0;
        if (totalLogin != null && totalLogin > 0) {
            double loginHours = totalLogin / 3600.0;
            callsPerHour = totalCalls / loginHours;
        } else if (!isMemberSpecific) {
            // Global fallback if no login time tracked: Total / 24H approx
            callsPerHour = totalCalls / 24.0;
        }

        Long totalWait = cdrRepository.sumTotalWaitTime();
        // 25. 평균 응답 속도 (ASA)
        // - 수식: 총 대기 시간(Wait Duration) 합계 / 응답된 총 통화 수 [단위: 초]
        Double asa = (!isMemberSpecific && answeredCalls > 0 && totalWait != null) ? (double) totalWait / answeredCalls
                : 0.0;

        // 28. 호 전환율 (Transfer Rate)
        // - 수식: (다른 상담원/부서로 호전환된 건수 / 총 처리 건수) * 100 [단위: %]
        Long transferCount = isMemberSpecific ? callRepository.sumTransferCountByMemberId(memberId)
                : callRepository.sumTransferCount();
        Double transferRate = totalCalls > 0 && transferCount != null ? (double) transferCount / totalCalls * 100 : 0.0;

        // 26. 평균 처리 시간 (AHT) - 수식: (총 통화 시간(Talk) + 총 대기 시간(Hold) + 총 후처리 시간(ACW)) /
        // 처리된 총 통화 수 [단위: 초/분] (여기선 avgDuration 사용)
        // 27. 평균 대기(보류) 시간 (Avg Hold Time) - 수식: 총 보류 시간(Hold Duration) / 보류가 발생한 통화 수
        // [단위: 초] (Pending)
        // 29. 평균 후처리 시간 (Avg ACW Time) - 수식: 총 후처리 시간(ACW Duration) / 처리된 총 통화 수 [단위:
        // 초] (Pending)
        // 21. 상담원 이직률 - 수식: (해당 기간 퇴사자 수 / 평균 재직자 수) * 100 [단위: %] (Pending)

        DashboardKpiResponseDto.AgentProductivityKpi agentProductivity = DashboardKpiResponseDto.AgentProductivityKpi
                .builder()
                .attritionRate(calculateAttritionRate(isMemberSpecific))
                .occupancyRate(formatDouble(occupancy))
                .scheduleAdherence(formatDouble(adherence))
                .callsPerHour(formatDouble(callsPerHour))
                .asa(formatDouble(asa))
                .aht(avgDuration != null ? avgDuration : 0.0)
                .avgHoldTime((avgDuration != null ? avgDuration : 0.0) * 0.1) // Estimate: 10% of Duration
                .transferRate(formatDouble(transferRate))
                .avgAcwTime((avgDuration != null ? avgDuration : 0.0) * 0.2) // Estimate: 20% of Duration
                .build();

        return DashboardKpiResponseDto.builder()
                .summary(summary)
                .callPerformance(callPerformance)
                .operations(operations)
                .agentProductivity(agentProductivity)
                .build();
    }

    private Double calculateFcr(boolean isMemberSpecific, Long memberId) {
        Long unique;
        Long repeat;
        if (isMemberSpecific) {
            unique = callRepository.countTotalUniqueCustomersByMemberId(memberId);
            repeat = callRepository.countRepeatCustomersByMemberId(memberId);
        } else {
            unique = callRepository.countTotalUniqueCustomers();
            repeat = callRepository.countRepeatCustomers();
        }

        if (unique != null && unique > 0) {
            return (double) (unique - (repeat != null ? repeat : 0)) / unique * 100;
        }
        return 0.0;
    }

    private Double calculateRepeatCallRate(boolean isMemberSpecific, Long memberId, double totalCalls) {
        if (totalCalls == 0)
            return 0.0;
        Long repeat;
        if (isMemberSpecific) {
            repeat = callRepository.countRepeatCustomersByMemberId(memberId);
        } else {
            repeat = callRepository.countRepeatCustomers();
        }
        return repeat != null ? (double) repeat / totalCalls * 100 : 0.0;
    }

    private Double formatDouble(Double val) {
        if (val == null)
            return 0.0;
        return BigDecimal.valueOf(val).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

    private Double calculateSelfServiceRate(boolean isMemberSpecific, long totalCalls) {
        if (isMemberSpecific || totalCalls == 0) {
            return 0.0;
        }
        long selfServiceCalls = callRepository.countByMemberIsNull();
        return (double) selfServiceCalls / totalCalls * 100;
    }

    private Double calculateAttritionRate(boolean isMemberSpecific) {
        if (isMemberSpecific) {
            return 0.0;
        }
        long totalMembers = memberRepository.count();
        if (totalMembers == 0)
            return 0.0;
        long resignedMembers = memberRepository.countByStatus("RESIGNED");
        return (double) resignedMembers / totalMembers * 100;
    }
}
