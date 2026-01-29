package com.twelvegg.aicc.mydatabase.call.domain;

import jakarta.persistence.*;
import lombok.*;
import com.twelvegg.aicc.mydatabase.tenant.domain.Tenant;
import com.twelvegg.aicc.mydatabase.customer.domain.Customer;
import com.twelvegg.aicc.mydatabase.member.domain.Member;
import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "calls")
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Call {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String phoneNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tenant_id")
    private Tenant tenant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder.Default
    @OneToMany(mappedBy = "call")
    private List<Transcript> transcripts = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "call")
    private List<RealtimeAnalysis> realtimeAnalyses = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "call")
    private List<ViolenceEvent> violenceEvents = new ArrayList<>();

    @OneToOne(mappedBy = "call")
    private PostCallSummary postCallSummary;

    @Builder.Default
    @OneToMany(mappedBy = "call")
    private List<AbnormalCase> abnormalCases = new ArrayList<>();

    // 전환 횟수
    private Integer transferCount;
    // 추정 비용
    private BigDecimal estimatedCost;

    // wav 파일 경로
    private String audioPath;
    // 통화 유형
    private String callType;
    // 통화 시작 시간
    private LocalDateTime startTime;
    // 통화 종료 시간
    private LocalDateTime endTime;
    // 통화 시간
    private Long duration;
    // billsec
    private Long billsec;
}
