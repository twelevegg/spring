package com.twelvegg.aicc.mydatabase.product.service;

import com.twelvegg.aicc.mydatabase.product.domain.IptvPlan;
import com.twelvegg.aicc.mydatabase.product.dto.IptvPlanResponseDto;

public interface IptvPlanService {
    IptvPlanResponseDto findById(Long id);

    IptvPlan save(IptvPlan iptvPlan);
}
