package com.twelvegg.aicc.cdr.repository;

import com.twelvegg.aicc.cdr.domain.Cdr;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CdrRepository extends JpaRepository<Cdr, Long> {

    // 통화 상태에 따른 통화 횟수 (통화 상태: ANSWERED, NO ANSWER, BUSY, FAILED)
    long countByDisposition(String disposition);

    long countByStartBetween(LocalDateTime start, LocalDateTime end);

    // 평균 통화 시간
    @Query("SELECT AVG(c.duration) FROM Cdr c")
    Double findAvgDuration();

    // 평균 응답 시간
    @Query("SELECT AVG(c.billsec) FROM Cdr c WHERE c.billsec > 0")
    Double findAvgBillsec();

    // 대기 시간 평균 (Answer - Start)
    @Query("SELECT AVG(TIMESTAMPDIFF(SECOND, c.start, c.answer)) FROM Cdr c WHERE c.answer IS NOT NULL")
    Double findAvgWaitTime();

    // 대기 시간 최대값
    @Query("SELECT MAX(TIMESTAMPDIFF(SECOND, c.start, c.answer)) FROM Cdr c WHERE c.answer IS NOT NULL")
    Integer findMaxWaitTime();

    // 평균 대기 시간이 가장 높은 시간대
    @Query("SELECT HOUR(c.start), COUNT(c) FROM Cdr c GROUP BY HOUR(c.start) ORDER BY COUNT(c) DESC")
    List<Object[]> findPeakHourTraffic();

    // 응답 시간 총합
    @Query("SELECT SUM(TIMESTAMPDIFF(SECOND, c.start, c.answer)) FROM Cdr c WHERE c.answer IS NOT NULL")
    Long sumTotalWaitTime();

    long countByAnswerIsNotNull();

    // 활성화된 통화 수
    @Query("SELECT COUNT(c) FROM Cdr c WHERE c.end IS NULL")
    long countActiveCalls();
}
