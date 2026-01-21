package com.twelvegg.aicc.mydatabase.product.repository;

import com.twelvegg.aicc.mydatabase.product.domain.BundleProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BundleProductRepository extends JpaRepository<BundleProduct, Long> {
}
