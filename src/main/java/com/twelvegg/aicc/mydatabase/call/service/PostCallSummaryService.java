package com.twelvegg.aicc.mydatabase.call.service;

import com.twelvegg.aicc.mydatabase.call.domain.PostCallSummary;
import com.twelvegg.aicc.mydatabase.call.dto.PostCallSummaryResponseDto;

public interface PostCallSummaryService {
    PostCallSummaryResponseDto findById(Long id);

    PostCallSummary save(PostCallSummary postCallSummary);
}
