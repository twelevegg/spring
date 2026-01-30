package com.twelvegg.aicc.mydatabase.caselibrary.dto;

import com.twelvegg.aicc.mydatabase.caselibrary.domain.CaseLibrary;
import lombok.Builder;

import java.time.LocalDate;
import java.util.List;

@Builder
public record CaseLibraryResponseDto(
        String id,
        String title,
        LocalDate date,
        List<String> tags,
        String body) {
    public static CaseLibraryResponseDto from(CaseLibrary entity) {
        return CaseLibraryResponseDto.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .date(entity.getDate())
                .tags(entity.getTags())
                .body(entity.getBody())
                .build();
    }
}
