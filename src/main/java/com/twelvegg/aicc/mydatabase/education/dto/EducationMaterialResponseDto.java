package com.twelvegg.aicc.mydatabase.education.dto;

import com.twelvegg.aicc.mydatabase.education.domain.EducationMaterial;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class EducationMaterialResponseDto {
    private Long id;
    private String originalFileName;
    private String fileType;
    private String contentType;
    private Long fileSize;
    private LocalDateTime createdAt;
    private String s3Url;

    public static EducationMaterialResponseDto from(EducationMaterial entity) {
        return EducationMaterialResponseDto.builder()
                .id(entity.getId())
                .originalFileName(entity.getOriginalFileName())
                .fileType(entity.getFileType().name())
                .contentType(entity.getContentType())
                .fileSize(entity.getFileSize())
                .createdAt(entity.getCreatedAt())
                .s3Url(entity.getS3Url())
                // storedFileName은 보안상 클라이언트에 노출하지 않음 (선택사항)
                .build();
    }
}
