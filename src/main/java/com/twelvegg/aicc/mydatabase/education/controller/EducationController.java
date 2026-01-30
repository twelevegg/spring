package com.twelvegg.aicc.mydatabase.education.controller;

import com.twelvegg.aicc.mydatabase.education.domain.EducationMaterial;
import com.twelvegg.aicc.mydatabase.education.service.EducationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "교육 자료 컨트롤러", description = "교육용 PPT/PDF 업로드 및 관리")
@RestController
@RequestMapping("/api/education/materials")
@RequiredArgsConstructor
public class EducationController {

    private final EducationService educationService;

    @Operation(summary = "교육 자료 보안 업로드")
    @PostMapping(value = "/secure-upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<EducationMaterial> uploadSecurely(@RequestPart("file") MultipartFile file) {
        return ResponseEntity.ok(educationService.uploadSecurely(file));
    }
}
