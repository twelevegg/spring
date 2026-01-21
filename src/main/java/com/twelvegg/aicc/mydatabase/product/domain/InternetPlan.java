package com.twelvegg.aicc.mydatabase.product.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "internet_plans")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class InternetPlan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String category;
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(columnDefinition = "TEXT")
    private String price;

    @Column(columnDefinition = "TEXT")
    private String baseBenefit;
}
