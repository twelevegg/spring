package com.twelvegg.aicc.mydatabase.caselibrary.dto;

import com.twelvegg.aicc.mydatabase.caselibrary.domain.CaseLibrary;
import lombok.Builder;

import java.time.LocalDate;
import java.util.List;

@Builder
public record CaseLibraryResponseDto(
        String caseLibraryId,
        Long memberId,
        String title,
        LocalDate date,
        List<String> tags,
        String body) {
    public static CaseLibraryResponseDto from(CaseLibrary entity) {
        return CaseLibraryResponseDto.builder()
                .caseLibraryId(entity.getCaseLibraryId())
                .memberId(entity.getMember().getId())
                .title(entity.getTitle())
                .date(entity.getDate())
                .tags(entity.getTags())
                .body(entity.getBody())
                .build();
    }
}
