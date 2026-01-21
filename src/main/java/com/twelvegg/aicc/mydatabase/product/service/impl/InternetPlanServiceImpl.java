package com.twelvegg.aicc.mydatabase.product.service.impl;

import com.twelvegg.aicc.exception.CustomException;
import com.twelvegg.aicc.exception.ErrorCode;
import com.twelvegg.aicc.mydatabase.product.domain.InternetPlan;
import com.twelvegg.aicc.mydatabase.product.dto.InternetPlanResponseDto;
import com.twelvegg.aicc.mydatabase.product.repository.InternetPlanRepository;
import com.twelvegg.aicc.mydatabase.product.service.InternetPlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class InternetPlanServiceImpl implements InternetPlanService {

    private final InternetPlanRepository internetPlanRepository;

    @Override
    public InternetPlanResponseDto findById(Long id) {
        InternetPlan internetPlan = internetPlanRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.RESOURCE_NOT_FOUND));
        return InternetPlanResponseDto.from(internetPlan);
    }

    @Override
    @Transactional
    public InternetPlan save(InternetPlan internetPlan) {
        return internetPlanRepository.save(internetPlan);
    }
}
