package com.twelvegg.aicc.mydatabase.member.domain;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;
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
    /**
     * Status: ACTIVE (Ready), AWAY (Break), ON_CALL (Busy), RESIGNED (Left)
     */
    private String status;
    private String email;
    private String password;

    private String shiftType;
    private LocalTime workStartTime;
    private LocalTime workEndTime;

    @Builder.Default
    @OneToMany(mappedBy = "member")
    private List<MemberMetric> metrics = new ArrayList<>();

    // 마지막 상태 변경 시간
    private LocalDateTime lastStatusUpdateTime;

    public void updatePassword(String password) {
        this.password = password;
    }

    public void updateStatus(String status) {
        this.status = status;
        this.lastStatusUpdateTime = LocalDateTime.now();
    }

    public void withdraw() {
        this.status = "RESIGNED";
        this.lastStatusUpdateTime = LocalDateTime.now();
        
        // 이메일 난수화: 재가입 허용 및 개인정보 보호
        // 예: user@example.com -> deleted_timestamp_user@example.com
        this.email = "deleted_" + System.currentTimeMillis() + "_" + this.email;
        
        // 이름 익명화
        this.name = "Unknown User";
        
        // 비밀번호 파기 (랜덤 문자열로 변경하여 로그인 불가능하게 처리)
        this.password = java.util.UUID.randomUUID().toString();
    }
}
