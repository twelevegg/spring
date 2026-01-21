package com.twelvegg.aicc.mydatabase.product.dto;

import com.twelvegg.aicc.mydatabase.product.domain.IptvPlan;
import lombok.Builder;

@Builder
public record IptvPlanResponseDto(
        Long id,
        String name,
        String description,
        String priceWithInternet,
        String priceTvOnly,
        String packageOption,
        String packageOptionDualPack2,
        String installFeeTvOnly,
        String installFeeExtra,
        String rentalFeeSettopBox,
        String baseBenefit,
        String extraBenefit) {
    public static IptvPlanResponseDto from(IptvPlan entity) {
        return IptvPlanResponseDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .priceWithInternet(entity.getPriceWithInternet())
                .priceTvOnly(entity.getPriceTvOnly())
                .packageOption(entity.getPackageOption())
                .packageOptionDualPack2(entity.getPackageOptionDualPack2())
                .installFeeTvOnly(entity.getInstallFeeTvOnly())
                .installFeeExtra(entity.getInstallFeeExtra())
                .rentalFeeSettopBox(entity.getRentalFeeSettopBox())
                .baseBenefit(entity.getBaseBenefit())
                .extraBenefit(entity.getExtraBenefit())
                .build();
    }
}
