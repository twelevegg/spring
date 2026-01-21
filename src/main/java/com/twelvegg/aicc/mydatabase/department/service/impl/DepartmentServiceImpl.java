package com.twelvegg.aicc.mydatabase.department.service.impl;

import com.twelvegg.aicc.exception.CustomException;
import com.twelvegg.aicc.exception.ErrorCode;
import com.twelvegg.aicc.mydatabase.department.domain.Department;
import com.twelvegg.aicc.mydatabase.department.dto.DepartmentResponseDto;
import com.twelvegg.aicc.mydatabase.department.repository.DepartmentRepository;
import com.twelvegg.aicc.mydatabase.department.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;

    @Override
    public DepartmentResponseDto findById(Long id) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.RESOURCE_NOT_FOUND));
        return DepartmentResponseDto.from(department);
    }

    @Override
    @Transactional
    public Department save(Department department) {
        return departmentRepository.save(department);
    }
}
