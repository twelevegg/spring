package com.twelvegg.aicc.mydatabase.tenant.domain;

import com.twelvegg.aicc.mydatabase.call.domain.Call;
import com.twelvegg.aicc.mydatabase.department.domain.Department;
import com.twelvegg.aicc.mydatabase.member.domain.Member;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tenants")
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Tenant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;
    private String status;
    private LocalDateTime createdAt;

    @Builder.Default
    @OneToMany(mappedBy = "tenant")
    private List<Department> departments = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "tenant")
    private List<Member> members = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "tenant")
    private List<Call> calls = new ArrayList<>();
}
