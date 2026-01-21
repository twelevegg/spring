package com.twelvegg.aicc.mydatabase.product.service.impl;

import com.twelvegg.aicc.exception.CustomException;
import com.twelvegg.aicc.exception.ErrorCode;
import com.twelvegg.aicc.mydatabase.product.domain.MobilePlan;
import com.twelvegg.aicc.mydatabase.product.dto.MobilePlanResponseDto;
import com.twelvegg.aicc.mydatabase.product.repository.MobilePlanRepository;
import com.twelvegg.aicc.mydatabase.product.service.MobilePlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MobilePlanServiceImpl implements MobilePlanService {

    private final MobilePlanRepository mobilePlanRepository;

    @Override
    public MobilePlanResponseDto findById(Long id) {
        MobilePlan mobilePlan = mobilePlanRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.RESOURCE_NOT_FOUND));
        return MobilePlanResponseDto.from(mobilePlan);
    }

    @Override
    @Transactional
    public MobilePlan save(MobilePlan mobilePlan) {
        return mobilePlanRepository.save(mobilePlan);
    }
}
