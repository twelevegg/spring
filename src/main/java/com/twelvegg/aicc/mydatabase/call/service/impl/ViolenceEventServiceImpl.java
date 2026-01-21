package com.twelvegg.aicc.mydatabase.call.service.impl;

import com.twelvegg.aicc.exception.CustomException;
import com.twelvegg.aicc.exception.ErrorCode;
import com.twelvegg.aicc.mydatabase.call.domain.ViolenceEvent;
import com.twelvegg.aicc.mydatabase.call.dto.ViolenceEventResponseDto;
import com.twelvegg.aicc.mydatabase.call.repository.ViolenceEventRepository;
import com.twelvegg.aicc.mydatabase.call.service.ViolenceEventService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ViolenceEventServiceImpl implements ViolenceEventService {

    private final ViolenceEventRepository violenceEventRepository;

    @Override
    public ViolenceEventResponseDto findById(Long id) {
        ViolenceEvent violenceEvent = violenceEventRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.RESOURCE_NOT_FOUND));
        return ViolenceEventResponseDto.from(violenceEvent);
    }

    @Override
    @Transactional
    public ViolenceEvent save(ViolenceEvent violenceEvent) {
        return violenceEventRepository.save(violenceEvent);
    }
}
