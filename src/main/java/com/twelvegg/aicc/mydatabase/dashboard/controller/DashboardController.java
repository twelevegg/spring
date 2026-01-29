package com.twelvegg.aicc.mydatabase.dashboard.controller;

import com.twelvegg.aicc.mydatabase.dashboard.dto.DashboardKpiResponseDto;
import com.twelvegg.aicc.mydatabase.dashboard.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
// @RequestMapping("/api/v1/dashboards")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping("/admin/kpi")
    public ResponseEntity<DashboardKpiResponseDto> getGlobalKpi() {
        DashboardKpiResponseDto kpi = dashboardService.getGlobalKpi();
        return ResponseEntity.ok(kpi);
    }

    @GetMapping("/my/kpi")
    public ResponseEntity<DashboardKpiResponseDto> getMemberKpi(@RequestAttribute("memberId") Long memberId) {
        DashboardKpiResponseDto kpi = dashboardService.getMemberKpi(memberId);
        return ResponseEntity.ok(kpi);
    }

    @GetMapping("/my/kpi/test/{memberId}")
    public ResponseEntity<DashboardKpiResponseDto> getMemberKpi2(@PathVariable Long memberId) {
        DashboardKpiResponseDto kpi = dashboardService.getMemberKpi(memberId);
        return ResponseEntity.ok(kpi);
    }
}
