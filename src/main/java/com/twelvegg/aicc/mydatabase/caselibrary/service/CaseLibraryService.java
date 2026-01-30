package com.twelvegg.aicc.mydatabase.caselibrary.service;

import com.twelvegg.aicc.mydatabase.caselibrary.dto.CaseLibraryRequestDto;
import com.twelvegg.aicc.mydatabase.caselibrary.dto.CaseLibraryResponseDto;

import java.util.List;

public interface CaseLibraryService {
    List<CaseLibraryResponseDto> findAll();

    CaseLibraryResponseDto findById(String id);

    CaseLibraryResponseDto create(Long memberId, CaseLibraryRequestDto request);

    CaseLibraryResponseDto update(String id, Long memberId, CaseLibraryRequestDto request);

    void delete(String id);
}
