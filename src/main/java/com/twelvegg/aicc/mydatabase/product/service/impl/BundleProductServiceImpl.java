package com.twelvegg.aicc.mydatabase.product.service.impl;

import com.twelvegg.aicc.exception.CustomException;
import com.twelvegg.aicc.exception.ErrorCode;
import com.twelvegg.aicc.mydatabase.product.domain.BundleProduct;
import com.twelvegg.aicc.mydatabase.product.dto.BundleProductResponseDto;
import com.twelvegg.aicc.mydatabase.product.repository.BundleProductRepository;
import com.twelvegg.aicc.mydatabase.product.service.BundleProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BundleProductServiceImpl implements BundleProductService {

    private final BundleProductRepository bundleProductRepository;

    @Override
    public BundleProductResponseDto findById(Long id) {
        BundleProduct bundleProduct = bundleProductRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.RESOURCE_NOT_FOUND));
        return BundleProductResponseDto.from(bundleProduct);
    }

    @Override
    @Transactional
    public BundleProduct save(BundleProduct bundleProduct) {
        return bundleProductRepository.save(bundleProduct);
    }
}
