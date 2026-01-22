package com.twelvegg.aicc.mydatabase.department.controller;

import com.twelvegg.aicc.mydatabase.department.dto.DepartmentResponseDto;
import com.twelvegg.aicc.mydatabase.department.service.DepartmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "부서 관리", description = "부서 정보 관리 API")
@RestController
@RequestMapping("/api/v1/departments")
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentService departmentService;

    @Operation(summary = "부서 단건 조회", description = "ID로 부서 정보를 조회합니다.")
    @GetMapping("/{id}")
    public ResponseEntity<DepartmentResponseDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(departmentService.findById(id));
    }
}
