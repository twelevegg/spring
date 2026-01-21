package com.twelvegg.aicc.mydatabase.member.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "member_metrics")
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class MemberMetric {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private Integer callCount;
    private Double stressScore;
    private Double burnoutRisk;
}
