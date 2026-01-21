package com.twelvegg.aicc.mydatabase.call.service.impl;

import com.twelvegg.aicc.exception.CustomException;
import com.twelvegg.aicc.exception.ErrorCode;
import com.twelvegg.aicc.mydatabase.call.domain.PostCallSummary;
import com.twelvegg.aicc.mydatabase.call.dto.PostCallSummaryResponseDto;
import com.twelvegg.aicc.mydatabase.call.repository.PostCallSummaryRepository;
import com.twelvegg.aicc.mydatabase.call.service.PostCallSummaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostCallSummaryServiceImpl implements PostCallSummaryService {

    private final PostCallSummaryRepository postCallSummaryRepository;

    @Override
    public PostCallSummaryResponseDto findById(Long id) {
        PostCallSummary postCallSummary = postCallSummaryRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.RESOURCE_NOT_FOUND));
        return PostCallSummaryResponseDto.from(postCallSummary);
    }

    @Override
    @Transactional
    public PostCallSummary save(PostCallSummary postCallSummary) {
        return postCallSummaryRepository.save(postCallSummary);
    }
}
