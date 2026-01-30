package com.twelvegg.aicc.mydatabase.education.service.impl;

import com.twelvegg.aicc.mydatabase.education.domain.EducationMaterial;
import com.twelvegg.aicc.mydatabase.education.dto.EducationMaterialResponseDto;
import com.twelvegg.aicc.mydatabase.education.repository.EducationMaterialRepository;
import com.twelvegg.aicc.mydatabase.education.service.EducationService;
import com.twelvegg.aicc.s3.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EducationServiceImpl implements EducationService {

    private final S3Service s3Service;
    private final EducationMaterialRepository educationMaterialRepository;

    @Override
    @Transactional
    public EducationMaterialResponseDto uploadSecurely(MultipartFile file) {
        // 1. 파일 비어있는지 체크
        if (file.isEmpty()) {
            throw new IllegalArgumentException("파일이 비어있습니다.");
        }

        // 2. 파일명 경로 조작(Path Traversal) 방지 & 원본 파일명 추출
        String originalFilename = FilenameUtils.getName(file.getOriginalFilename());
        if (originalFilename.contains("..")) {
            throw new SecurityException("파일명에 허용되지 않는 문자가 포함되어 있습니다.");
        }

        // 3. 확장자 검사 (Allow List 방식)
        String ext = FilenameUtils.getExtension(originalFilename).toLowerCase();
        EducationMaterial.MaterialType type;
        if (ext.equals("pdf")) {
            type = EducationMaterial.MaterialType.PDF;
        } else if (ext.equals("pptx") || ext.equals("ppt")) {
            type = EducationMaterial.MaterialType.PPT;
        } else {
            throw new IllegalArgumentException("허용되지 않는 파일 형식입니다. (PDF, PPTX만 가능)");
        }

        // 4. 파일명 난수화 (UUID)
        String uuid = UUID.randomUUID().toString();
        String storedFileName = uuid + "." + ext;

        // 5. S3에 업로드 (난수화된 이름 사용)
        String s3Url = s3Service.upload(file, "education", storedFileName);

        // 6. DB에 메타데이터 저장
        EducationMaterial material = EducationMaterial.builder()
                .originalFileName(originalFilename) // 원본 이름
                .storedFileName(storedFileName)     // 난수화된 이름 (S3 Key)
                .s3Url(s3Url)
                .fileType(type)
                .fileSize(file.getSize())
                .contentType(file.getContentType())
                .build();

        EducationMaterial saved = educationMaterialRepository.save(material);
        return EducationMaterialResponseDto.from(saved);
    }
}
