package com.twelvegg.aicc.mydatabase.call.domain;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;

@Entity
@Table(name = "post_call_summaries")
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class PostCallSummary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "call_id")
    private Call call;

    @Column(columnDefinition = "TEXT")
    private String summaryText;

    // 고객 노력 점수 (Customer Effort Score)
    private Double cesScore;

    // 고객 만족도 (Customer Satisfaction Score) 0~100% 척도
    private Double csatScore;

    // 순추천 고객 지수 (친구나 동료에게 추천할 가능성이 얼마나 높은지)
    /**
     * 추천 고객(9~10점)
     * 중립 고객(7~8점)
     * 비추천 고객(0~6점)
     */
    private Double npsScore;

    // 감정 분석
    private Double sentimentScore;

    // 키워드
    private String keyword;

    @CreatedDate
    private LocalDateTime createdAt;
}
