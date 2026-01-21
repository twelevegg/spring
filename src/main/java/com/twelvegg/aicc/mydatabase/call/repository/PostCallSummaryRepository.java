package com.twelvegg.aicc.mydatabase.call.repository;

import com.twelvegg.aicc.mydatabase.call.domain.PostCallSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostCallSummaryRepository extends JpaRepository<PostCallSummary, Long> {
}
