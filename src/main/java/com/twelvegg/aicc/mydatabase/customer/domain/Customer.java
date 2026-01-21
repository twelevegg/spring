package com.twelvegg.aicc.mydatabase.customer.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import com.twelvegg.aicc.mydatabase.product.domain.InternetPlan;
import com.twelvegg.aicc.mydatabase.product.domain.MobilePlan;
import com.twelvegg.aicc.mydatabase.product.domain.IptvPlan;
import com.twelvegg.aicc.mydatabase.product.domain.BundleProduct;
import com.twelvegg.aicc.mydatabase.call.domain.Call;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "customers")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "internet_plan_id")
    private InternetPlan internetPlan;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mobile_plan_id")
    private MobilePlan mobilePlan;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "iptv_plan_id")
    private IptvPlan iptvPlan;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bundle_product_id")
    private BundleProduct bundleProduct;

    private String name;
    private Integer age;
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

    @Builder.Default
    @OneToMany(mappedBy = "customer")
    private List<Call> calls = new ArrayList<>();
}
