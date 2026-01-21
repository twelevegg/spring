package com.twelvegg.aicc.mydatabase.tenant.service;

import com.twelvegg.aicc.mydatabase.tenant.domain.Tenant;
import com.twelvegg.aicc.mydatabase.tenant.dto.TenantResponseDto;

public interface TenantService {
    TenantResponseDto findById(Long id);

    Tenant save(Tenant tenant);
}
