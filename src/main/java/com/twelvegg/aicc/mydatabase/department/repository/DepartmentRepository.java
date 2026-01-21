package com.twelvegg.aicc.mydatabase.department.repository;

import com.twelvegg.aicc.mydatabase.department.domain.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
}
