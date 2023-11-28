package com.phung.clothshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.phung.clothshop.model.product.ProductImage;

@Repository
public interface ProductImageRepository extends JpaRepository<ProductImage, Long>,
                JpaSpecificationExecutor<ProductImage> {

}
