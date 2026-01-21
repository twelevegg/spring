package com.twelvegg.aicc.mydatabase.product.dto;

import com.twelvegg.aicc.mydatabase.product.domain.MobilePlan;
import lombok.Builder;

@Builder
public record MobilePlanResponseDto(
        Long id,
        String type,
        String name,
        String description,
        String price,
        String condition,
        String notes,
        String providedData,
        String sharedData,
        String voice,
        String message,
        String smartDevice,
        String baseBenefit,
        String specialBenefit,
        String soldierBenefit,
        String extraDiscountBenefit) {
    public static MobilePlanResponseDto from(MobilePlan entity) {
        return MobilePlanResponseDto.builder()
                .id(entity.getId())
                .type(entity.getType())
                .name(entity.getName())
                .description(entity.getDescription())
                .price(entity.getPrice())
                .condition(entity.getCondition())
                .notes(entity.getNotes())
                .providedData(entity.getProvidedData())
                .sharedData(entity.getSharedData())
                .voice(entity.getVoice())
                .message(entity.getMessage())
                .smartDevice(entity.getSmartDevice())
                .baseBenefit(entity.getBaseBenefit())
                .specialBenefit(entity.getSpecialBenefit())
                .soldierBenefit(entity.getSoldierBenefit())
                .extraDiscountBenefit(entity.getExtraDiscountBenefit())
                .build();
    }
}
