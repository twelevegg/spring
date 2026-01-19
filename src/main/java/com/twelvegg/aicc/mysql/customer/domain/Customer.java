package com.twelvegg.aicc.mysql.customer.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.AllArgsConstructor;
import lombok.ToString;

@Getter
@Builder
@AllArgsConstructor
@ToString
public class Customer {
    private Long id;
    private Long internetPlanId;
    private Long mobilePlanId;
    private Long iptvPlanId;
    private Long bundleProductId;
    private String name;
    private String age;
    private String gender;
    private String phoneNumber;
    private String isForeigner;
    private int contractPeriodMonths;
    private int remainingContractMonths;
    private String isOptionalContract;
    private String hasWelfareCard;
    private String overageLastMonth1;
    private String overageLastMonth2;
    private String isDataCarryOver;
    private String isDataSharing;
    private String householdType;
    private String isRemoteWork;
}
