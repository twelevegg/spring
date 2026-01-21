package com.twelvegg.aicc.mydatabase.call.domain;

import jakarta.persistence.*;
import lombok.*;
import com.twelvegg.aicc.mydatabase.tenant.domain.Tenant;
import com.twelvegg.aicc.mydatabase.customer.domain.Customer;
import java.util.ArrayList;
import java.util.List;

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
}
