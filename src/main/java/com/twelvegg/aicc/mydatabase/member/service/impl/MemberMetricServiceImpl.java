package com.twelvegg.aicc.mydatabase.member.service.impl;

import com.twelvegg.aicc.exception.CustomException;
import com.twelvegg.aicc.exception.ErrorCode;
import com.twelvegg.aicc.mydatabase.member.domain.MemberMetric;
import com.twelvegg.aicc.mydatabase.member.dto.MemberMetricResponseDto;
import com.twelvegg.aicc.mydatabase.member.repository.MemberMetricRepository;
import com.twelvegg.aicc.mydatabase.member.service.MemberMetricService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberMetricServiceImpl implements MemberMetricService {

    private final MemberMetricRepository memberMetricRepository;

    @Override
    public MemberMetricResponseDto findById(Long id) {
        MemberMetric memberMetric = memberMetricRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.RESOURCE_NOT_FOUND));
        return MemberMetricResponseDto.from(memberMetric);
    }

    @Override
    @Transactional
    public MemberMetric save(MemberMetric memberMetric) {
        return memberMetricRepository.save(memberMetric);
    }
}
