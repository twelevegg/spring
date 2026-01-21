package com.twelvegg.aicc.mydatabase.call.service.impl;

import com.twelvegg.aicc.exception.CustomException;
import com.twelvegg.aicc.exception.ErrorCode;
import com.twelvegg.aicc.mydatabase.call.domain.AbnormalCase;
import com.twelvegg.aicc.mydatabase.call.dto.AbnormalCaseResponseDto;
import com.twelvegg.aicc.mydatabase.call.repository.AbnormalCaseRepository;
import com.twelvegg.aicc.mydatabase.call.service.AbnormalCaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AbnormalCaseServiceImpl implements AbnormalCaseService {

    private final AbnormalCaseRepository abnormalCaseRepository;

    @Override
    public AbnormalCaseResponseDto findById(Long id) {
        AbnormalCase abnormalCase = abnormalCaseRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.RESOURCE_NOT_FOUND));
        return AbnormalCaseResponseDto.from(abnormalCase);
    }

    @Override
    @Transactional
    public AbnormalCase save(AbnormalCase abnormalCase) {
        return abnormalCaseRepository.save(abnormalCase);
    }
}
