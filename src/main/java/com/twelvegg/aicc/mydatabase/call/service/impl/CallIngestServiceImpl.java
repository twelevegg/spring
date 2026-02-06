package com.twelvegg.aicc.mydatabase.call.service.impl;

import com.twelvegg.aicc.mydatabase.call.domain.Call;
import com.twelvegg.aicc.mydatabase.call.domain.PostCallSummary;
import com.twelvegg.aicc.mydatabase.call.domain.Transcript;
import com.twelvegg.aicc.mydatabase.call.dto.CallEndRequestDto;
import com.twelvegg.aicc.mydatabase.call.dto.CallEndResponseDto;
import com.twelvegg.aicc.mydatabase.call.dto.CallTranscriptRequestDto;
import com.twelvegg.aicc.mydatabase.call.repository.CallRepository;
import com.twelvegg.aicc.mydatabase.call.repository.PostCallSummaryRepository;
import com.twelvegg.aicc.mydatabase.call.repository.TranscriptRepository;
import com.twelvegg.aicc.mydatabase.call.service.CallIngestService;
import com.twelvegg.aicc.mydatabase.customer.domain.Customer;
import com.twelvegg.aicc.mydatabase.customer.repository.CustomerRepository;
import com.twelvegg.aicc.mydatabase.member.domain.Member;
import com.twelvegg.aicc.mydatabase.member.repository.MemberRepository;
import com.twelvegg.aicc.mydatabase.tenant.domain.Tenant;
import com.twelvegg.aicc.mydatabase.tenant.repository.TenantRepository;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CallIngestServiceImpl implements CallIngestService {

    private final CallRepository callRepository;
    private final TranscriptRepository transcriptRepository;
    private final PostCallSummaryRepository postCallSummaryRepository;
    private final CustomerRepository customerRepository;
    private final TenantRepository tenantRepository;
    private final MemberRepository memberRepository;

    private static final java.time.format.DateTimeFormatter FORMATTER = java.time.format.DateTimeFormatter
            .ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public CallEndResponseDto saveCallEnd(CallEndRequestDto request) {
        LocalDateTime now = LocalDateTime.now();

        Customer customer = null;
        if (request.customerNumber() != null && !request.customerNumber().isBlank()) {
            customer = customerRepository.findByPhoneNumber(request.customerNumber())
                    .orElse(null);
        }

        Tenant tenant = customer != null ? customer.getTenant() : null;
        if (tenant == null && request.tenantName() != null && !request.tenantName().isBlank()) {
            tenant = tenantRepository.findByName(request.tenantName()).orElse(null);
        }

        Member member = null;
        if (request.memberId() != null) {
            member = memberRepository.findById(request.memberId()).orElse(null);
        }

        LocalDateTime start = now;
        if (request.startTime() != null && !request.startTime().isBlank()) {
            try {
                start = LocalDateTime.parse(request.startTime(), FORMATTER);
            } catch (Exception e) {
                // Formatting error handled by defaulting to 'now' or logging
            }
        }

        LocalDateTime end = now;
        if (request.endTime() != null && !request.endTime().isBlank()) {
            try {
                end = LocalDateTime.parse(request.endTime(), FORMATTER);
            } catch (Exception e) {
                // Formatting error handled
            }
        }

        Call call = callRepository.save(Call.builder()
                .phoneNumber(request.customerNumber())
                .customer(customer)
                .tenant(tenant)
                .member(member)
                .transferCount(0)
                .estimatedCost(request.estimatedCost() != null
                        ? BigDecimal.valueOf(request.estimatedCost())
                        : null)
                .callType("ANSWERED")
                .startTime(start)
                .endTime(end)
                .duration(request.duration() != null ? request.duration() : 0L)
                .billsec(request.billsec() != null ? request.billsec() : 0L)
                .audioPath("/mnt/data/audio/" + UUID.randomUUID().toString() + ".wav")
                .build());

        int transcriptCount = 0;
        if (request.transcripts() != null && !request.transcripts().isEmpty()) {
            List<Transcript> transcripts = new ArrayList<>();
            for (CallTranscriptRequestDto item : request.transcripts()) {
                transcripts.add(Transcript.builder()
                        .call(call)
                        .speaker(item.speaker())
                        .content(item.transcript())
                        .timestamp(now)
                        .isFinal("Y")
                        .build());
            }
            transcriptRepository.saveAll(transcripts);
            transcriptCount = transcripts.size();
        }

        boolean summarySaved = false;
        if (hasSummaryPayload(request)) {
            PostCallSummary summary = PostCallSummary.builder()
                    .call(call)
                    .summaryText(request.summaryText())
                    .cesScore(request.cesScore())
                    .csatScore(request.csatScore())
                    .npsScore(request.rpsScore())
                    .sentimentScore(null)
                    .keyword(joinKeywords(request.keyword()))
                    .createdAt(now)
                    .build();
            postCallSummaryRepository.save(summary);
            summarySaved = true;
        }

        return CallEndResponseDto.builder()
                .callId(call.getId())
                .transcriptCount(transcriptCount)
                .summarySaved(summarySaved)
                .build();
    }

    private boolean hasSummaryPayload(CallEndRequestDto request) {
        return request.summaryText() != null
                || request.cesScore() != null
                || request.csatScore() != null
                || request.rpsScore() != null
                || (request.keyword() != null && !request.keyword().isEmpty());
    }

    private String joinKeywords(List<String> keywords) {
        if (keywords == null || keywords.isEmpty()) {
            return null;
        }
        return String.join(",", keywords);
    }
}
