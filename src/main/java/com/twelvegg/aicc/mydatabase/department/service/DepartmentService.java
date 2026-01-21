package com.twelvegg.aicc.mydatabase.department.service;

import com.twelvegg.aicc.mydatabase.department.domain.Department;
import com.twelvegg.aicc.mydatabase.department.dto.DepartmentResponseDto;

public interface DepartmentService {
    DepartmentResponseDto findById(Long id);

    Department save(Department department);
}
