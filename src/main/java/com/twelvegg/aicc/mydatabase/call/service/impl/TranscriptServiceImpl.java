package com.twelvegg.aicc.mydatabase.call.service.impl;

import com.twelvegg.aicc.exception.CustomException;
import com.twelvegg.aicc.exception.ErrorCode;
import com.twelvegg.aicc.mydatabase.call.domain.Transcript;
import com.twelvegg.aicc.mydatabase.call.dto.TranscriptResponseDto;
import com.twelvegg.aicc.mydatabase.call.repository.TranscriptRepository;
import com.twelvegg.aicc.mydatabase.call.service.TranscriptService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TranscriptServiceImpl implements TranscriptService {

    private final TranscriptRepository transcriptRepository;

    @Override
    public TranscriptResponseDto findById(Long id) {
        Transcript transcript = transcriptRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.RESOURCE_NOT_FOUND));
        return TranscriptResponseDto.from(transcript);
    }

    @Override
    @Transactional
    public Transcript save(Transcript transcript) {
        return transcriptRepository.save(transcript);
    }
}
