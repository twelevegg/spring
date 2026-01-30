package com.twelvegg.aicc.mydatabase.caselibrary.dto;

import java.util.List;

public record CaseLibraryRequestDto(
        String title,
        String body,
        List<String> tags) {
}
