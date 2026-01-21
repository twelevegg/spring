package com.twelvegg.aicc.mydatabase.call.service;

import com.twelvegg.aicc.mydatabase.call.domain.AbnormalCase;
import com.twelvegg.aicc.mydatabase.call.dto.AbnormalCaseResponseDto;

public interface AbnormalCaseService {
    AbnormalCaseResponseDto findById(Long id);

    AbnormalCase save(AbnormalCase abnormalCase);
}
