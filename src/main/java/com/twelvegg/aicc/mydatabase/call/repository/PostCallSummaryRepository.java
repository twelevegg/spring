package com.twelvegg.aicc.mydatabase.call.repository;

import com.twelvegg.aicc.mydatabase.call.domain.PostCallSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface PostCallSummaryRepository extends JpaRepository<PostCallSummary, Long> {

    @Query("SELECT AVG(p.cesScore) FROM PostCallSummary p")
    Double findAvgCesScore();

    @Query("SELECT AVG(p.csatScore) FROM PostCallSummary p")
    Double findAvgCsatScore();

    @Query("SELECT AVG(p.npsScore) FROM PostCallSummary p")
    Double findAvgNpsScore();

    @Query("SELECT AVG(p.sentimentScore) FROM PostCallSummary p")
    Double findAvgSentimentScore();

    // 멤버 별 지수
    @Query("SELECT AVG(p.cesScore) FROM PostCallSummary p WHERE p.call.member.id = :memberId")
    Double findAvgCesScoreByMemberId(@Param("memberId") Long memberId);

    @Query("SELECT AVG(p.csatScore) FROM PostCallSummary p WHERE p.call.member.id = :memberId")
    Double findAvgCsatScoreByMemberId(@Param("memberId") Long memberId);

    @Query("SELECT AVG(p.npsScore) FROM PostCallSummary p WHERE p.call.member.id = :memberId")
    Double findAvgNpsScoreByMemberId(@Param("memberId") Long memberId);

    @Query("SELECT AVG(p.sentimentScore) FROM PostCallSummary p WHERE p.call.member.id = :memberId")
    Double findAvgSentimentScoreByMemberId(@Param("memberId") Long memberId);
}
