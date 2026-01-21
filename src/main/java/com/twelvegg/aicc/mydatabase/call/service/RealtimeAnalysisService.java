package com.twelvegg.aicc.mydatabase.call.service;

import com.twelvegg.aicc.mydatabase.call.domain.RealtimeAnalysis;
import com.twelvegg.aicc.mydatabase.call.dto.RealtimeAnalysisResponseDto;

public interface RealtimeAnalysisService {
    RealtimeAnalysisResponseDto findById(Long id);

    RealtimeAnalysis save(RealtimeAnalysis realtimeAnalysis);

}
