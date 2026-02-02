package com.twelvegg.aicc.mydatabase.education.service;

import com.twelvegg.aicc.mydatabase.education.dto.EducationMaterialResponseDto;
import org.springframework.web.multipart.MultipartFile;

public interface EducationService {
    EducationMaterialResponseDto uploadSecurely(MultipartFile file);
}
