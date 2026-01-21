package com.twelvegg.aicc.mydatabase.product.repository;

import com.twelvegg.aicc.mydatabase.product.domain.MobilePlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MobilePlanRepository extends JpaRepository<MobilePlan, Long> {
}
