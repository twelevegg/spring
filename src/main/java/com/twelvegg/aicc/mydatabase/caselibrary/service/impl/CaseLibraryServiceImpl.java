package com.twelvegg.aicc.mydatabase.caselibrary.service.impl;

import com.twelvegg.aicc.exception.CustomException;
import com.twelvegg.aicc.exception.ErrorCode;
import com.twelvegg.aicc.mydatabase.caselibrary.domain.CaseLibrary;
import com.twelvegg.aicc.mydatabase.caselibrary.dto.CaseLibraryRequestDto;
import com.twelvegg.aicc.mydatabase.caselibrary.dto.CaseLibraryResponseDto;
import com.twelvegg.aicc.mydatabase.caselibrary.repository.CaseLibraryRepository;
import com.twelvegg.aicc.mydatabase.caselibrary.service.CaseLibraryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CaseLibraryServiceImpl implements CaseLibraryService {

    private static final Pattern ID_PATTERN = Pattern.compile("K-(\\d+)", Pattern.CASE_INSENSITIVE);

    private final CaseLibraryRepository caseLibraryRepository;

    @Override
    public List<CaseLibraryResponseDto> findAll() {
        return caseLibraryRepository.findAllByOrderByDateDescIdDesc()
                .stream()
                .map(CaseLibraryResponseDto::from)
                .toList();
    }

    @Override
    public CaseLibraryResponseDto findById(String id) {
        CaseLibrary caseLibrary = caseLibraryRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.RESOURCE_NOT_FOUND));
        return CaseLibraryResponseDto.from(caseLibrary);
    }

    @Override
    @Transactional
    public CaseLibraryResponseDto create(CaseLibraryRequestDto request) {
        String nextId = nextCaseId();
        CaseLibrary caseLibrary = CaseLibrary.builder()
                .id(nextId)
                .title(request.title())
                .body(request.body())
                .tags(Optional.ofNullable(request.tags()).orElse(List.of()))
                .date(LocalDate.now())
                .build();

        CaseLibrary saved = caseLibraryRepository.save(caseLibrary);
        return CaseLibraryResponseDto.from(saved);
    }

    @Override
    @Transactional
    public CaseLibraryResponseDto update(String id, CaseLibraryRequestDto request) {
        CaseLibrary caseLibrary = caseLibraryRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.RESOURCE_NOT_FOUND));

        caseLibrary.update(
                request.title(),
                request.body(),
                request.tags(),
                LocalDate.now()
        );

        return CaseLibraryResponseDto.from(caseLibrary);
    }

    @Override
    @Transactional
    public void delete(String id) {
        CaseLibrary caseLibrary = caseLibraryRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.RESOURCE_NOT_FOUND));
        caseLibraryRepository.delete(caseLibrary);
    }

    private String nextCaseId() {
        int nextNumber = caseLibraryRepository.findTopByOrderByIdDesc()
                .map(CaseLibrary::getId)
                .map(this::extractNumber)
                .orElse(0) + 1;

        return "K-" + String.format(Locale.ENGLISH, "%03d", nextNumber);
    }

    private int extractNumber(String id) {
        if (id == null) {
            return 0;
        }
        Matcher matcher = ID_PATTERN.matcher(id.trim());
        if (matcher.matches()) {
            return Integer.parseInt(matcher.group(1));
        }
        return 0;
    }
}
