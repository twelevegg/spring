package com.twelvegg.aicc.s3.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.twelvegg.aicc.s3.service.S3Service;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "S3 컨트롤러", description = "S3 관련 API")
@RequestMapping("")
@RequiredArgsConstructor
@RestController
public class S3Controller {

    private final S3Service s3Service;

    @Operation(summary = "S3 파일 업로드 (wav만 가능)")
    @PostMapping(value = "/s3/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadFile(@RequestPart("file") MultipartFile file) {
        return ResponseEntity.ok(s3Service.upload(file, "test"));
    }

}
