package com.twelvegg.aicc.mydatabase.product.service.impl;

import com.twelvegg.aicc.exception.CustomException;
import com.twelvegg.aicc.exception.ErrorCode;
import com.twelvegg.aicc.mydatabase.product.domain.IptvPlan;
import com.twelvegg.aicc.mydatabase.product.dto.IptvPlanResponseDto;
import com.twelvegg.aicc.mydatabase.product.repository.IptvPlanRepository;
import com.twelvegg.aicc.mydatabase.product.service.IptvPlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class IptvPlanServiceImpl implements IptvPlanService {

    private final IptvPlanRepository iptvPlanRepository;

    @Override
    public IptvPlanResponseDto findById(Long id) {
        IptvPlan iptvPlan = iptvPlanRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.RESOURCE_NOT_FOUND));
        return IptvPlanResponseDto.from(iptvPlan);
    }

    @Override
    @Transactional
    public IptvPlan save(IptvPlan iptvPlan) {
        return iptvPlanRepository.save(iptvPlan);
    }
}
