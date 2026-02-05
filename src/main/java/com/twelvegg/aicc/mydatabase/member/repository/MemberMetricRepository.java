package com.twelvegg.aicc.mydatabase.member.repository;

import com.twelvegg.aicc.mydatabase.member.domain.MemberMetric;
import com.twelvegg.aicc.mydatabase.member.domain.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface MemberMetricRepository extends JpaRepository<MemberMetric, Long> {

    @Query("SELECT SUM(m.totalLoginTime) FROM MemberMetric m")
    Long sumTotalLoginTime();

    @Query("SELECT SUM(m.totalTalkTime) FROM MemberMetric m")
    Long sumTotalTalkTime();

    @Query("SELECT SUM(m.totalBreakTime) FROM MemberMetric m")
    Long sumTotalBreakTime();

    @Query("SELECT SUM(m.totalReadyTime) FROM MemberMetric m")
    Long sumTotalReadyTime();

    @Query("SELECT AVG(m.scheduleAdherenceScore) FROM MemberMetric m")
    Double findAvgScheduleAdherence();

    // 멤버 별 지수
    @Query("SELECT SUM(m.totalLoginTime) FROM MemberMetric m WHERE m.member.id = :memberId")
    Long sumTotalLoginTimeByMemberId(@Param("memberId") Long memberId);

    @Query("SELECT SUM(m.totalTalkTime) FROM MemberMetric m WHERE m.member.id = :memberId")
    Long sumTotalTalkTimeByMemberId(@Param("memberId") Long memberId);

    @Query("SELECT SUM(m.totalBreakTime) FROM MemberMetric m WHERE m.member.id = :memberId")
    Long sumTotalBreakTimeByMemberId(@Param("memberId") Long memberId);

    @Query("SELECT SUM(m.totalReadyTime) FROM MemberMetric m WHERE m.member.id = :memberId")
    Long sumTotalReadyTimeByMemberId(@Param("memberId") Long memberId);

    @Query("SELECT AVG(m.scheduleAdherenceScore) FROM MemberMetric m WHERE m.member.id = :memberId")
    Double findAvgScheduleAdherenceByMemberId(@Param("memberId") Long memberId);

    Optional<MemberMetric> findByMember(Member member);
}
