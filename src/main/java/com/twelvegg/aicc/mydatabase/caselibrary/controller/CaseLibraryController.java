package com.twelvegg.aicc.mydatabase.caselibrary.controller;

import com.twelvegg.aicc.mydatabase.caselibrary.dto.CaseLibraryRequestDto;
import com.twelvegg.aicc.mydatabase.caselibrary.dto.CaseLibraryResponseDto;
import com.twelvegg.aicc.mydatabase.caselibrary.service.CaseLibraryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "상담 지식 베이스", description = "상담 사례 게시판 CRUD API")
@RestController
@RequestMapping("/api/v1/case-library")
@RequiredArgsConstructor
public class CaseLibraryController {

    private final CaseLibraryService caseLibraryService;

    @Operation(summary = "케이스 목록 조회", description = "상담 지식 베이스 케이스 목록을 조회합니다.")
    @GetMapping
    public ResponseEntity<List<CaseLibraryResponseDto>> findAll() {
        return ResponseEntity.ok(caseLibraryService.findAll());
    }

    @Operation(summary = "케이스 단건 조회", description = "ID로 상담 케이스 정보를 조회합니다.")
    @GetMapping("/{id}")
    public ResponseEntity<CaseLibraryResponseDto> findById(@PathVariable String id) {
        return ResponseEntity.ok(caseLibraryService.findById(id));
    }

    @Operation(summary = "케이스 생성", description = "상담 케이스를 새로 등록합니다.")
    @PostMapping
    public ResponseEntity<CaseLibraryResponseDto> create(
            @RequestAttribute("memberId") Long memberId,
            @RequestBody CaseLibraryRequestDto request
    ) {
        return ResponseEntity.ok(caseLibraryService.create(memberId, request));
    }

    @Operation(summary = "케이스 수정", description = "상담 케이스 정보를 수정합니다.")
    @PutMapping("/{id}")
    public ResponseEntity<CaseLibraryResponseDto> update(
            @PathVariable String id,
            @RequestAttribute("memberId") Long memberId,
            @RequestBody CaseLibraryRequestDto request
    ) {
        return ResponseEntity.ok(caseLibraryService.update(id, memberId, request));
    }

    @Operation(summary = "케이스 삭제", description = "상담 케이스를 삭제합니다.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        caseLibraryService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
