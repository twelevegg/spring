package com.twelvegg.aicc.mydatabase.call.service.impl;

import com.twelvegg.aicc.exception.CustomException;
import com.twelvegg.aicc.exception.ErrorCode;
import com.twelvegg.aicc.mydatabase.call.domain.Call;
import com.twelvegg.aicc.mydatabase.call.dto.CallResponseDto;
import com.twelvegg.aicc.mydatabase.call.repository.CallRepository;
import com.twelvegg.aicc.mydatabase.call.repository.PostCallSummaryRepository;
import com.twelvegg.aicc.mydatabase.call.repository.TranscriptRepository;
import com.twelvegg.aicc.mydatabase.customer.repository.CustomerRepository;
import com.twelvegg.aicc.mydatabase.call.service.CallService;
import com.twelvegg.aicc.mydatabase.call.domain.PostCallSummary;
import com.twelvegg.aicc.mydatabase.call.domain.Transcript;
import com.twelvegg.aicc.mydatabase.customer.domain.Customer;
import com.twelvegg.aicc.mydatabase.call.dto.CallEndRequestDto;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.twelvegg.aicc.mydatabase.call.dto.CallAnalysisResponseDto;
import com.twelvegg.aicc.mydatabase.call.dto.TranscriptDto;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CallServiceImpl implements CallService {

    private final CallRepository callRepository;
    private final PostCallSummaryRepository postCallSummaryRepository;
    private final TranscriptRepository transcriptRepository;
    private final CustomerRepository customerRepository;

    @Override
    public CallResponseDto findById(Long id) {
        Call call = callRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.RESOURCE_NOT_FOUND));
        return CallResponseDto.from(call);
    }

    @Override
    @Transactional
    public Call save(Call call) {
        return callRepository.save(call);
    }

    @Override
    public String getAudioPath(Long callId) {
        Call call = callRepository.findById(callId)
                .orElseThrow(() -> new CustomException(ErrorCode.RESOURCE_NOT_FOUND));
        return call.getAudioPath();
    }

    @Override
    public List<TranscriptDto> getTranscripts(Long callId) {
        Call call = callRepository.findById(callId)
                .orElseThrow(() -> new CustomException(ErrorCode.RESOURCE_NOT_FOUND));
        return call.getTranscripts().stream()
                .map(TranscriptDto::from)
                .collect(Collectors.toList());
    }

    @Override
    public CallAnalysisResponseDto getAnalysis(Long callId) {
        Call call = callRepository.findById(callId)
                .orElseThrow(() -> new CustomException(ErrorCode.RESOURCE_NOT_FOUND));

        if (call.getPostCallSummary() == null) {
            return null;
        }

        return CallAnalysisResponseDto.builder()
                .callId(call.getId())
                .summaryText(call.getPostCallSummary().getSummaryText())
                .cesScore(call.getPostCallSummary().getCesScore())
                .csatScore(call.getPostCallSummary().getCsatScore())
                .npsScore(call.getPostCallSummary().getNpsScore())
                .sentimentScore(call.getPostCallSummary().getSentimentScore())
                .keyword(call.getPostCallSummary().getKeyword())
                .analyzedAt(call.getPostCallSummary().getCreatedAt())
                .build();
    }

    @Override
    @Transactional
    public void saveCallLog(CallEndRequestDto requestDto) {
        // 1. 고객 조회 (전화번호로 조회, 없으면 null 처리 또는 기본 고객 할당)
        // CustomerRepository에 findByPhoneNumber가 있다고 가정
        Customer customer = null;
        if (requestDto.customerNumber() != null) {
            // S2S 호출이므로 "default" 테넌트로 가정하고 조회
            customer = customerRepository.findByPhoneNumberAndTenant_Name(requestDto.customerNumber(), "default").orElse(null);
        }

        // 2. Call 생성
        Call call = Call.builder()
                .customer(customer)
                .phoneNumber(requestDto.customerNumber())
                .estimatedCost(requestDto.estimatedCost() != null ? BigDecimal.valueOf(requestDto.estimatedCost()) : null)
                .startTime(LocalDateTime.now()) // 임시: 현재 시간
                .endTime(LocalDateTime.now())   // 임시: 현재 시간
                .build();
        callRepository.save(call);

        // 3. PostCallSummary 생성
        String keywords = requestDto.keyword() != null ? String.join(",", requestDto.keyword()) : "";
        PostCallSummary summary = PostCallSummary.builder()
                .call(call)
                .summaryText(requestDto.summaryText())
                .cesScore(requestDto.cesScore())
                .csatScore(requestDto.csatScore())
                .npsScore(requestDto.rpsScore())
                .keyword(keywords)
                .build();
        postCallSummaryRepository.save(summary);

        // 4. Transcript 생성
        if (requestDto.transcripts() != null) {
            List<Transcript> transcripts = requestDto.transcripts().stream()
                .map(t -> Transcript.builder()
                        .call(call)
                        .speaker(t.speaker())
                        .content(t.transcript())
                        .timestamp(LocalDateTime.now())
                        .build())
                .collect(Collectors.toList());
            transcriptRepository.saveAll(transcripts);
        }
    }
}
