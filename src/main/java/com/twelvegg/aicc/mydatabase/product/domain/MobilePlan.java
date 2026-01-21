package com.twelvegg.aicc.mydatabase.product.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "mobile_plans")
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class MobilePlan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type;
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String price;
    private String condition;
    private String notes;
    private String providedData;
    private String sharedData;
    private String voice;
    private String message;
    private String smartDevice;

    @Column(columnDefinition = "TEXT")
    private String baseBenefit;

    @Column(columnDefinition = "TEXT")
    private String specialBenefit;

    @Column(columnDefinition = "TEXT")
    private String soldierBenefit;

    @Column(columnDefinition = "TEXT")
    private String extraDiscountBenefit;
}
