package com.phung.clothshop.repository;

import java.util.Optional;

import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.phung.clothshop.model.product.Product;
import com.phung.clothshop.model.dto.ProductPaginationReqDTO;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>,
        JpaSpecificationExecutor<Product> {

    default Page<Product> getAllPagination(ProductPaginationReqDTO productShowReqDTO, Pageable pageable) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }

    boolean existsByName(String name);

}
