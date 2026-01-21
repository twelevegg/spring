package com.twelvegg.aicc.mydatabase.product.service;

import com.twelvegg.aicc.mydatabase.product.domain.InternetPlan;
import com.twelvegg.aicc.mydatabase.product.dto.InternetPlanResponseDto;

public interface InternetPlanService {
    InternetPlanResponseDto findById(Long id);

    InternetPlan save(InternetPlan internetPlan);
}
