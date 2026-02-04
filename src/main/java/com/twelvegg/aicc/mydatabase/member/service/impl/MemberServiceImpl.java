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
