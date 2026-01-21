package com.twelvegg.aicc.mydatabase.product.dto;

import com.twelvegg.aicc.mydatabase.product.domain.BundleProduct;
import lombok.Builder;

@Builder
public record BundleProductResponseDto(
        Long id,
        String type,
        String name,
        String description,
        String discountBenefit,
        String condition,
        String extraCondition,
        String notes,
        String requiredDocuments,
        String applyMethod,
        String canApply,
        String details) {
    public static BundleProductResponseDto from(BundleProduct entity) {
        return BundleProductResponseDto.builder()
                .id(entity.getId())
                .type(entity.getType())
                .name(entity.getName())
                .description(entity.getDescription())
                .discountBenefit(entity.getDiscountBenefit())
                .condition(entity.getCondition())
                .extraCondition(entity.getExtraCondition())
                .notes(entity.getNotes())
                .requiredDocuments(entity.getRequiredDocuments())
                .applyMethod(entity.getApplyMethod())
                .canApply(entity.getCanApply())
                .details(entity.getDetails())
                .build();
    }
}
