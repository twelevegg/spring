package com.twelvegg.aicc.mydatabase.call.repository;

import com.twelvegg.aicc.mydatabase.call.domain.RealtimeAnalysis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RealtimeAnalysisRepository extends JpaRepository<RealtimeAnalysis, Long> {
}
