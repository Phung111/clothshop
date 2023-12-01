package com.phung.clothshop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.web.multipart.MultipartFile;

import com.phung.clothshop.model.dto.ProductCreateReqDTO;
import com.phung.clothshop.model.dto.ProductImageDTO;
import com.phung.clothshop.model.dto.ProductResDTO;
import com.phung.clothshop.model.dto.ProductPaginationReqDTO;
import com.phung.clothshop.model.dto.ProductShowResDTO;
import com.phung.clothshop.model.dto.ProductUpdateReqDTO;
import com.phung.clothshop.model.product.Product;

public interface IProductService extends IGeneralService<Product, Long> {

    Page<ProductResDTO> getAllPagination(ProductPaginationReqDTO productShowReqDTO, Pageable pageable);

    ProductResDTO createWithImage(ProductCreateReqDTO productCreateReqDTO, MultipartFile[] multipartFiles);

    ProductResDTO createNoImage(ProductCreateReqDTO productCreateReqDTO);

    ProductResDTO updateWithImage(List<Long> idImageDeletes, Product product, MultipartFile[] multipartFiles);

    ProductResDTO updateNoImage(List<Long> idImageDeletes, Product product);

    boolean existsByName(String name);

}
