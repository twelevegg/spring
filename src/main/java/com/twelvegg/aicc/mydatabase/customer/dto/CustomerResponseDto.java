package com.twelvegg.aicc.mydatabase.customer.dto;

import com.twelvegg.aicc.mydatabase.customer.domain.Customer;
import lombok.Builder;

@Builder
public record CustomerResponseDto(
        Long id,
        Long internetPlanId,
        Long mobilePlanId,
        Long iptvPlanId,
        Long bundleProductId,
        String name,
        Integer age,
        String gender,
        String phoneNumber,
        String isForeigner,
        int contractPeriodMonths,
        int remainingContractMonths,
        String isOptionalContract,
        String hasWelfareCard,
        String overageLastMonth1,
        String overageLastMonth2,
        String isDataCarryOver,
        String isDataSharing,
        String householdType,
        String isRemoteWork) {
    public static CustomerResponseDto from(Customer entity) {
        return CustomerResponseDto.builder()
                .id(entity.getId())
                .internetPlanId(entity.getInternetPlan() != null ? entity.getInternetPlan().getId() : null)
                .mobilePlanId(entity.getMobilePlan() != null ? entity.getMobilePlan().getId() : null)
                .iptvPlanId(entity.getIptvPlan() != null ? entity.getIptvPlan().getId() : null)
                .bundleProductId(entity.getBundleProduct() != null ? entity.getBundleProduct().getId() : null)
                .name(entity.getName())
                .age(entity.getAge())
                .gender(entity.getGender())
                .phoneNumber(entity.getPhoneNumber())
                .isForeigner(entity.getIsForeigner())
                .contractPeriodMonths(entity.getContractPeriodMonths())
                .remainingContractMonths(entity.getRemainingContractMonths())
                .isOptionalContract(entity.getIsOptionalContract())
                .hasWelfareCard(entity.getHasWelfareCard())
                .overageLastMonth1(entity.getOverageLastMonth1())
                .overageLastMonth2(entity.getOverageLastMonth2())
                .isDataCarryOver(entity.getIsDataCarryOver())
                .isDataSharing(entity.getIsDataSharing())
                .householdType(entity.getHouseholdType())
                .isRemoteWork(entity.getIsRemoteWork())
                .build();
    }
}
