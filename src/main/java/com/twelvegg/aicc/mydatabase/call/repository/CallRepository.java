package com.twelvegg.aicc.mydatabase.call.repository;

import com.twelvegg.aicc.mydatabase.call.domain.Call;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.math.BigDecimal;

@Repository
public interface CallRepository extends JpaRepository<Call, Long> {

    @Query("SELECT SUM(c.estimatedCost) FROM Call c")
    BigDecimal sumEstimatedCost();

    @Query("SELECT SUM(c.estimatedCost) FROM Call c WHERE c.member.id = :memberId")
    BigDecimal sumEstimatedCostByMemberId(@Param("memberId") Long memberId);

    @Query("SELECT SUM(c.transferCount) FROM Call c")
    Long sumTransferCount();

    @Query("SELECT SUM(c.transferCount) FROM Call c WHERE c.member.id = :memberId")
    Long sumTransferCountByMemberId(@Param("memberId") Long memberId);

    long countByMemberId(Long memberId);

    // FCR 계산
    @Query("SELECT COUNT(DISTINCT c.customer.id) FROM Call c")
    Long countTotalUniqueCustomers();

    @Query("SELECT COUNT(DISTINCT c.customer.id) FROM Call c WHERE c.customer.id IN (SELECT c2.customer.id FROM Call c2 GROUP BY c2.customer.id HAVING COUNT(c2) > 1)")
    Long countRepeatCustomers();

    @Query("SELECT COUNT(DISTINCT c.customer.id) FROM Call c WHERE c.member.id = :memberId")
    Long countTotalUniqueCustomersByMemberId(@Param("memberId") Long memberId);

    @Query("SELECT COUNT(DISTINCT c.customer.id) FROM Call c WHERE c.member.id = :memberId AND c.customer.id IN (SELECT c2.customer.id FROM Call c2 WHERE c2.member.id = :memberId GROUP BY c2.customer.id HAVING COUNT(c2) > 1)")
    Long countRepeatCustomersByMemberId(@Param("memberId") Long memberId);

    // 활성 대기 통화 (Active Calls) - PostCallSummary가 아직 없는 건들 (종료되지 않음 가정)
    long countByPostCallSummaryIsNull();

    long countByMemberIdAndPostCallSummaryIsNull(Long memberId);

    // Self Service Rate (상담원이 배정되지 않은 통화)
    long countByMemberIsNull();
}
