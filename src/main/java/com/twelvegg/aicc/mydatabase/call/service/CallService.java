package com.twelvegg.aicc.mydatabase.call.service;

import com.twelvegg.aicc.mydatabase.call.domain.Call;
import com.twelvegg.aicc.mydatabase.call.dto.CallResponseDto;
import com.twelvegg.aicc.mydatabase.call.dto.TranscriptDto;
import com.twelvegg.aicc.mydatabase.call.dto.CallAnalysisResponseDto;
import com.twelvegg.aicc.mydatabase.call.dto.CallDetailResponseDto;
import java.util.List;

public interface CallService {
    CallResponseDto findById(Long id);

    Call save(Call call);

    String getAudioPath(Long callId);

    List<TranscriptDto> getTranscripts(Long callId);

    CallAnalysisResponseDto getAnalysis(Long callId);

    CallDetailResponseDto getDetail(Long callId);
}
