package com.twelvegg.aicc.mydatabase.member.repository;

import com.twelvegg.aicc.mydatabase.member.domain.MemberMetric;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberMetricRepository extends JpaRepository<MemberMetric, Long> {
}
