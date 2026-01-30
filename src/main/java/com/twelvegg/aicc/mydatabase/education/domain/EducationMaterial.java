package com.twelvegg.aicc.mydatabase.education.domain;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "education_material")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class EducationMaterial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 원본 파일명 (사용자 표시용)
    @Column(nullable = false)
    private String originalFileName;

    // 저장된 파일명 (난수화, S3 키)
    @Column(nullable = false)
    private String storedFileName;

    // S3 URL
    @Column(nullable = false)
    private String s3Url;

    // 파일 타입 (PPT, PDF)
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MaterialType fileType;

    // 파일 크기
    private Long fileSize;

    // 컨텐츠 타입
    private String contentType;

    // 생성일
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    public enum MaterialType {
        PPT, PDF
    }
}
