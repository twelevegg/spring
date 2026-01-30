package com.twelvegg.aicc.mydatabase.call.service.impl;

import com.twelvegg.aicc.exception.CustomException;
import com.twelvegg.aicc.exception.ErrorCode;
import com.twelvegg.aicc.mydatabase.call.domain.Call;
import com.twelvegg.aicc.mydatabase.call.dto.CallResponseDto;
import com.twelvegg.aicc.mydatabase.call.repository.CallRepository;
import com.twelvegg.aicc.mydatabase.call.service.CallService;
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
}
