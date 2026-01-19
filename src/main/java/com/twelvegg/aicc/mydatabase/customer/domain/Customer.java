package com.twelvegg.aicc.mydatabase.customer.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "customers")
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    @Column(name = "overage_last_month_1")
    private String overageLastMonth1;
    @Column(name = "overage_last_month_2")
    private String overageLastMonth2;
    private String isDataCarryOver;
    private String isDataSharing;
    private String householdType;
    private String isRemoteWork;
}
