package com.twelvegg.aicc.mydatabase.product.dto;

import com.twelvegg.aicc.mydatabase.product.domain.InternetPlan;
import lombok.Builder;

@Builder
public record InternetPlanResponseDto(
        Long id,
        String category,
        String name,
        String description,
        String price,
        String baseBenefit) {
    public static InternetPlanResponseDto from(InternetPlan entity) {
        return InternetPlanResponseDto.builder()
                .id(entity.getId())
                .category(entity.getCategory())
                .name(entity.getName())
                .description(entity.getDescription())
                .price(entity.getPrice())
                .baseBenefit(entity.getBaseBenefit())
                .build();
    }
}
