package com.twelvegg.aicc.mydatabase.tenant.service.impl;

import com.twelvegg.aicc.exception.CustomException;
import com.twelvegg.aicc.exception.ErrorCode;
import com.twelvegg.aicc.mydatabase.tenant.domain.Tenant;
import com.twelvegg.aicc.mydatabase.tenant.dto.TenantResponseDto;
import com.twelvegg.aicc.mydatabase.tenant.repository.TenantRepository;
import com.twelvegg.aicc.mydatabase.tenant.service.TenantService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TenantServiceImpl implements TenantService {

    private final TenantRepository tenantRepository;

    @Override
    public TenantResponseDto findById(Long id) {
        Tenant tenant = tenantRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.RESOURCE_NOT_FOUND));
        return TenantResponseDto.from(tenant);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TenantResponseDto> findAll() {
        return tenantRepository.findAll().stream()
                .map(TenantResponseDto::from)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Tenant save(Tenant tenant) {
        return tenantRepository.save(tenant);
    }
}
