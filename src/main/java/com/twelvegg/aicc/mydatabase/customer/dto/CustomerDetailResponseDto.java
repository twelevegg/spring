package com.twelvegg.aicc.mydatabase.customer.dto;

import com.twelvegg.aicc.mydatabase.product.dto.BundleProductResponseDto;
import com.twelvegg.aicc.mydatabase.product.dto.InternetPlanResponseDto;
import com.twelvegg.aicc.mydatabase.product.dto.IptvPlanResponseDto;
import com.twelvegg.aicc.mydatabase.product.dto.MobilePlanResponseDto;
import lombok.Builder;

@Builder
public record CustomerDetailResponseDto(
        CustomerResponseDto customer,
        InternetPlanResponseDto internetPlan,
        MobilePlanResponseDto mobilePlan,
        IptvPlanResponseDto iptvPlan,
        BundleProductResponseDto bundleProduct) {
}
