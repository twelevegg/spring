package com.twelvegg.aicc.mydatabase.call.service.impl;

import com.twelvegg.aicc.exception.CustomException;
import com.twelvegg.aicc.exception.ErrorCode;
import com.twelvegg.aicc.mydatabase.call.domain.Call;
import com.twelvegg.aicc.mydatabase.call.dto.CallResponseDto;
import com.twelvegg.aicc.mydatabase.call.repository.CallRepository;
import com.twelvegg.aicc.mydatabase.call.service.CallService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CallServiceImpl implements CallService {

    private final CallRepository callRepository;

    @Override
    public CallResponseDto findById(Long id) {
        Call call = callRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.RESOURCE_NOT_FOUND));
        return CallResponseDto.from(call);
    }

    @Override
    @Transactional
    public Call save(Call call) {
        return callRepository.save(call);
    }
}
