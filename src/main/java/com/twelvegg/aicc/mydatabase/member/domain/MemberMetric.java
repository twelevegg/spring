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

    // 총 활동 시간
    // 총 활동 시간 (단위: 초)
    private Long totalLoginTime;
    private Long totalBreakTime;
    private Long totalTalkTime;
    private Long totalReadyTime;

    // 근무 지수
    private Integer scheduleAdherenceScore; // %

    public void addTotalLoginTime(Long time) {
        this.totalLoginTime = (this.totalLoginTime == null ? 0 : this.totalLoginTime) + time;
    }

    public void addTotalBreakTime(Long time) {
        this.totalBreakTime = (this.totalBreakTime == null ? 0 : this.totalBreakTime) + time;
    }

    public void addTotalTalkTime(Long time) {
        this.totalTalkTime = (this.totalTalkTime == null ? 0 : this.totalTalkTime) + time;
    }

    public void addTotalReadyTime(Long time) {
        this.totalReadyTime = (this.totalReadyTime == null ? 0 : this.totalReadyTime) + time;
    }

    public void updateScheduleAdherenceScore(Integer score) {
        this.scheduleAdherenceScore = score;
    }
}
