package com.twelvegg.aicc.mydatabase.tenant.repository;

import com.twelvegg.aicc.mydatabase.tenant.domain.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TenantRepository extends JpaRepository<Tenant, Long> {
}
