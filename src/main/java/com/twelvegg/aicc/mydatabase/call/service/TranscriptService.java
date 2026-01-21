package com.twelvegg.aicc.mydatabase.call.service;

import com.twelvegg.aicc.mydatabase.call.domain.Transcript;
import com.twelvegg.aicc.mydatabase.call.dto.TranscriptResponseDto;

public interface TranscriptService {
    TranscriptResponseDto findById(Long id);

    Transcript save(Transcript transcript);
}
