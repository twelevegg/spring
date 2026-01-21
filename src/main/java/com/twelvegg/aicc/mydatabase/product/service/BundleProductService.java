package com.twelvegg.aicc.mydatabase.product.service;

import com.twelvegg.aicc.mydatabase.product.domain.BundleProduct;
import com.twelvegg.aicc.mydatabase.product.dto.BundleProductResponseDto;

public interface BundleProductService {
    BundleProductResponseDto findById(Long id);

    BundleProduct save(BundleProduct bundleProduct);
}
