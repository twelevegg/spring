package com.twelvegg.aicc.mydatabase.product.repository;

import com.twelvegg.aicc.mydatabase.product.domain.IptvPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IptvPlanRepository extends JpaRepository<IptvPlan, Long> {
}
