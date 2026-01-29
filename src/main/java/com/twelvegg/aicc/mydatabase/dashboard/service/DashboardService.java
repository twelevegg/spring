package com.twelvegg.aicc.mydatabase.dashboard.service;

import com.twelvegg.aicc.mydatabase.dashboard.dto.DashboardKpiResponseDto;

public interface DashboardService {
    DashboardKpiResponseDto getGlobalKpi();

    DashboardKpiResponseDto getMemberKpi(Long memberId);
}
