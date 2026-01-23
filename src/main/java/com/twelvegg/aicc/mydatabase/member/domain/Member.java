package com.twelvegg.aicc.mydatabase.member.domain;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import com.twelvegg.aicc.mydatabase.tenant.domain.Tenant;
import com.twelvegg.aicc.mydatabase.department.domain.Department;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "members")
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tenant_id")
    private Tenant tenant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    private Department department;
    private String name;
    private String role;
    private LocalDate hireDate;
    private String status;
    private String email;
    private String password;

    @Builder.Default
    @OneToMany(mappedBy = "member")
    private List<MemberMetric> metrics = new ArrayList<>();
}
