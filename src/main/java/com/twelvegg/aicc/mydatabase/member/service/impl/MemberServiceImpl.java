package com.twelvegg.aicc.mydatabase.member.service.impl;

import com.twelvegg.aicc.exception.CustomException;
import com.twelvegg.aicc.exception.ErrorCode;
import com.twelvegg.aicc.mydatabase.member.domain.Member;
import com.twelvegg.aicc.mydatabase.member.dto.MemberResponseDto;
import com.twelvegg.aicc.mydatabase.member.dto.MemberCallStatsDto;
import com.twelvegg.aicc.mydatabase.member.dto.MemberRecentCallDto;
import com.twelvegg.aicc.mydatabase.call.dto.CallDetailResponseDto;
import com.twelvegg.aicc.mydatabase.member.dto.MemberSummaryResponseDto;
import com.twelvegg.aicc.mydatabase.call.dto.TranscriptDto;
import com.twelvegg.aicc.mydatabase.call.domain.Call;
import com.twelvegg.aicc.mydatabase.call.repository.CallRepository;
import com.twelvegg.aicc.mydatabase.member.domain.MemberMetric;
import com.twelvegg.aicc.mydatabase.member.repository.MemberMetricRepository;
import com.twelvegg.aicc.mydatabase.member.repository.MemberRepository;
import com.twelvegg.aicc.mydatabase.member.service.MemberService;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final CallRepository callRepository;
    private final MemberMetricRepository memberMetricRepository;

    @Override
    public MemberResponseDto findById(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
        return MemberResponseDto.from(member);
    }

    @Override
    @Transactional
    public Member save(Member member) {
        return memberRepository.save(member);
    }

    @Override
    public MemberCallStatsDto getMemberCallStats(Long memberId) {
        LocalDateTime startOfDay = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
        LocalDateTime endOfDay = LocalDateTime.of(LocalDate.now(), LocalTime.MAX);

        long todayCallCount = callRepository.countByMember_IdAndStartTimeBetween(memberId, startOfDay, endOfDay);
        List<Call> recentCalls = callRepository.findTop3ByMember_IdOrderByStartTimeDesc(memberId);

        List<MemberRecentCallDto> recentCallDtos = recentCalls.stream()
                .map(call -> MemberRecentCallDto.builder()
                        .callId(call.getId())
                        .startTime(call.getStartTime())
                        .summaryText(
                                call.getPostCallSummary() != null ? call.getPostCallSummary().getSummaryText() : null)
                        .build())
                .collect(Collectors.toList());

        return MemberCallStatsDto.builder()
                .todayCallCount(todayCallCount)
                .recentCalls(recentCallDtos)
                .build();
    }

    @Override
    public Page<CallDetailResponseDto> getMemberCallHistory(Long memberId, LocalDate date, Pageable pageable) {
        Page<Call> callPage;
        if (date != null) {
            LocalDateTime startOfDay = LocalDateTime.of(date, LocalTime.MIN);
            LocalDateTime endOfDay = LocalDateTime.of(date, LocalTime.MAX);
            callPage = callRepository.findByMember_IdAndStartTimeBetween(memberId, startOfDay, endOfDay, pageable);
        } else {
            callPage = callRepository.findByMember_Id(memberId, pageable);
        }

        return callPage.map(this::toCallDetailResponseDto);
    }

    @Override
    public Long getMemberCallCount(Long memberId, LocalDate date) {
        if (date != null) {
            LocalDateTime startOfDay = LocalDateTime.of(date, LocalTime.MIN);
            LocalDateTime endOfDay = LocalDateTime.of(date, LocalTime.MAX);
            return callRepository.countByMember_IdAndStartTimeBetween(memberId, startOfDay, endOfDay);
        } else {
            return callRepository.countByMemberId(memberId);
        }
    }

    @Override
    public List<MemberSummaryResponseDto> getMemberSummaries() {
        return memberRepository.findAll().stream()
                .map(member -> MemberSummaryResponseDto.of(member, callRepository.countByMemberId(member.getId())))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public MemberResponseDto updateStatus(Long memberId, String status) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        // 이전 상태
        String oldStatus = member.getStatus();
        LocalDateTime lastUpdate = member.getLastStatusUpdateTime();
        LocalDateTime now = LocalDateTime.now();

        // MemberMetric 가져오기 (없으면 생성)
        MemberMetric metric = memberMetricRepository.findByMember(member)
                .orElseGet(() -> {
                    MemberMetric newMetric = MemberMetric.builder()
                            .member(member)
                            .totalLoginTime(0L)
                            .totalBreakTime(0L)
                            .totalTalkTime(0L)
                            .totalReadyTime(0L)
                            .scheduleAdherenceScore(0)
                            .build();
                    return memberMetricRepository.save(newMetric);
                });

        if (lastUpdate != null && oldStatus != null) {
            long duration = java.time.Duration.between(lastUpdate, now).getSeconds();

            // 이전 상태에 따라 시간 업데이트
            // Status: ACTIVE (Ready), AWAY (Break), ON_CALL (Busy), RESIGNED (Left)
            switch (oldStatus) {
                case "ACTIVE": // Ready
                    metric.addTotalReadyTime(duration);
                    metric.addTotalLoginTime(duration);
                    break;
                case "AWAY": // Break
                    metric.addTotalBreakTime(duration);
                    metric.addTotalLoginTime(duration);
                    break;
                case "ON_CALL": // Busy (Talk) -> Assuming ON_CALL includes talking time or is treated as talk
                                // time for now
                    metric.addTotalTalkTime(duration);
                    metric.addTotalLoginTime(duration);
                    break;
                default:
                    // Other statuses might not contribute to specific metrics or login time
                    break;
            }

            // 근무 지수 업데이트 (scheduleAdherenceScore)
            // ((totalReadyTime + totalTalkTime) / totalLoginTime) * 100
            long totalReady = metric.getTotalReadyTime() == null ? 0 : metric.getTotalReadyTime();
            long totalTalk = metric.getTotalTalkTime() == null ? 0 : metric.getTotalTalkTime();
            long totalLogin = metric.getTotalLoginTime() == null ? 0 : metric.getTotalLoginTime();

            if (totalLogin > 0) {
                int adherenceScore = (int) (((double) (totalReady + totalTalk) / totalLogin) * 100);
                metric.updateScheduleAdherenceScore(adherenceScore);
            }
        }

        member.updateStatus(status);
        return MemberResponseDto.from(member);
    }

    private CallDetailResponseDto toCallDetailResponseDto(Call call) {
        List<TranscriptDto> transcriptDtos = call.getTranscripts().stream()
                .map(TranscriptDto::from)
                .collect(Collectors.toList());

        return CallDetailResponseDto.builder()
                .id(call.getId())
                .phoneNumber(call.getPhoneNumber())
                .callType(call.getCallType())
                .startTime(call.getStartTime())
                .endTime(call.getEndTime())
                .duration(call.getDuration())
                .transferCount(call.getTransferCount())
                .estimatedCost(call.getEstimatedCost())
                .customerName(call.getCustomer() != null ? call.getCustomer().getName() : null)
                .customerAge(call.getCustomer() != null ? call.getCustomer().getAge() : null)
                .customerGender(call.getCustomer() != null ? call.getCustomer().getGender() : null)
                .customerPhone(call.getCustomer() != null ? call.getCustomer().getPhoneNumber() : null)
                .audioPath(call.getAudioPath())
                .summaryText(call.getPostCallSummary() != null ? call.getPostCallSummary().getSummaryText() : null)
                .keyword(call.getPostCallSummary() != null ? call.getPostCallSummary().getKeyword() : null) // Assuming
                                                                                                            // keyword
                                                                                                            // exists in
                                                                                                            // PostCallSummary
                .transcripts(transcriptDtos)
                .build();
    }
}
