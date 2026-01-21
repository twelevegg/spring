package com.twelvegg.aicc.mydatabase.product.repository;

import com.twelvegg.aicc.mydatabase.product.domain.InternetPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InternetPlanRepository extends JpaRepository<InternetPlan, Long> {
}
