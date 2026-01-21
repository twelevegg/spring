package com.twelvegg.aicc.mydatabase.call.service;

import com.twelvegg.aicc.mydatabase.call.domain.Call;
import com.twelvegg.aicc.mydatabase.call.dto.CallResponseDto;

public interface CallService {
    CallResponseDto findById(Long id);

    Call save(Call call);
}
