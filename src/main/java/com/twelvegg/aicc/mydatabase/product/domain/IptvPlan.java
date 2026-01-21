package com.twelvegg.aicc.mydatabase.product.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "iptv_plans")
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class IptvPlan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(columnDefinition = "TEXT")
    private String priceWithInternet;

    @Column(columnDefinition = "TEXT")
    private String priceTvOnly;

    @Column(columnDefinition = "TEXT")
    private String packageOption;

    @Column(columnDefinition = "TEXT")
    private String packageOptionDualPack2;

    @Column(columnDefinition = "TEXT")
    private String installFeeTvOnly;

    @Column(columnDefinition = "TEXT")
    private String installFeeExtra;

    @Column(columnDefinition = "TEXT")
    private String rentalFeeSettopBox;

    @Column(columnDefinition = "TEXT")
    private String baseBenefit;

    @Column(columnDefinition = "TEXT")
    private String extraBenefit;
}
