package com.phung.clothshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.phung.clothshop.domain.entity.product.ProductImage;

@Repository
public interface ProductImageRepository extends JpaRepository<ProductImage, Long>,
        JpaSpecificationExecutor<ProductImage> {
    List<ProductImage> findByProduct_Id(Long productId);
}
