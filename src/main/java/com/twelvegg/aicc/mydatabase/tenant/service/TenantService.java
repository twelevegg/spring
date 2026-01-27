package com.twelvegg.aicc.mydatabase.tenant.service;

import com.twelvegg.aicc.mydatabase.tenant.domain.Tenant;
import com.twelvegg.aicc.mydatabase.tenant.dto.TenantResponseDto;
import java.util.List;

public interface TenantService {
    TenantResponseDto findById(Long id);

    List<TenantResponseDto> findAll();

    Tenant save(Tenant tenant);
}
