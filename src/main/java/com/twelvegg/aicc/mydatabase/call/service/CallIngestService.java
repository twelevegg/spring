package com.twelvegg.aicc.mydatabase.call.service;

import com.twelvegg.aicc.mydatabase.call.dto.CallEndRequestDto;
import com.twelvegg.aicc.mydatabase.call.dto.CallEndResponseDto;

public interface CallIngestService {
    CallEndResponseDto saveCallEnd(CallEndRequestDto request);
}
