package com.twelvegg.aicc.mydatabase.call.service;

import com.twelvegg.aicc.mydatabase.call.domain.ViolenceEvent;
import com.twelvegg.aicc.mydatabase.call.dto.ViolenceEventResponseDto;

public interface ViolenceEventService {
    ViolenceEventResponseDto findById(Long id);

    ViolenceEvent save(ViolenceEvent violenceEvent);
}
