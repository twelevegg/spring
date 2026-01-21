package com.twelvegg.aicc.mydatabase.member.service;

import com.twelvegg.aicc.mydatabase.member.domain.MemberMetric;
import com.twelvegg.aicc.mydatabase.member.dto.MemberMetricResponseDto;

public interface MemberMetricService {
    MemberMetricResponseDto findById(Long id);

    MemberMetric save(MemberMetric memberMetric);
}
