package com.phung.clothshop.service;

import java.util.List;

import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import com.phung.clothshop.model.dto.ProductCreateReqDTO;
import com.phung.clothshop.model.dto.ProductImageDTO;
import com.phung.clothshop.model.dto.ProductResDTO;
import com.phung.clothshop.model.dto.ProductShowReqDTO;
import com.phung.clothshop.model.dto.ProductShowResDTO;
import com.phung.clothshop.model.dto.ProductUpdateReqDTO;
import com.phung.clothshop.model.product.Product;

public interface IProductService extends IGeneralService<Product, Long> {

    Page<ProductShowResDTO> findAll(ProductShowReqDTO productShowReqDTO, Pageable pageable);

    ProductResDTO createWithImage(ProductCreateReqDTO productCreateReqDTO, MultipartFile[] multipartFiles);

    ProductResDTO createNoImage(ProductCreateReqDTO productCreateReqDTO);

    ProductResDTO updateWithImage(ProductUpdateReqDTO productUpdateReqDTO, MultipartFile[] multipartFiles);

    ProductResDTO updateNoImage(ProductUpdateReqDTO productCreateReqDTO);

    boolean existsByName(String name);
}
