package com.twelvegg.aicc.mydatabase.call.service.impl;

import com.twelvegg.aicc.exception.CustomException;
import com.twelvegg.aicc.exception.ErrorCode;
import com.twelvegg.aicc.mydatabase.call.domain.RealtimeAnalysis;
import com.twelvegg.aicc.mydatabase.call.dto.RealtimeAnalysisResponseDto;
import com.twelvegg.aicc.mydatabase.call.repository.RealtimeAnalysisRepository;
import com.twelvegg.aicc.mydatabase.call.service.RealtimeAnalysisService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RealtimeAnalysisServiceImpl implements RealtimeAnalysisService {

    private final RealtimeAnalysisRepository realtimeAnalysisRepository;

    @Override
    public RealtimeAnalysisResponseDto findById(Long id) {
        RealtimeAnalysis realtimeAnalysis = realtimeAnalysisRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.RESOURCE_NOT_FOUND));
        return RealtimeAnalysisResponseDto.from(realtimeAnalysis);
    }

    @Override
    @Transactional
    public RealtimeAnalysis save(RealtimeAnalysis realtimeAnalysis) {
        return realtimeAnalysisRepository.save(realtimeAnalysis);
    }
}
