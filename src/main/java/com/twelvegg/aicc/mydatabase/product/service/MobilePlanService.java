package com.twelvegg.aicc.mydatabase.product.service;

import com.twelvegg.aicc.mydatabase.product.domain.MobilePlan;
import com.twelvegg.aicc.mydatabase.product.dto.MobilePlanResponseDto;

public interface MobilePlanService {
    MobilePlanResponseDto findById(Long id);

    MobilePlan save(MobilePlan mobilePlan);
}
