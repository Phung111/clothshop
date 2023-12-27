package com.phung.clothshop.service.product;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

import com.phung.clothshop.model.dto.product.ProductPageReqDTO;
import com.phung.clothshop.model.dto.product.ProductResDTO;
import com.phung.clothshop.model.product.Product;
import com.phung.clothshop.service.IGeneralService;

public interface IProductService extends IGeneralService<Product, Long> {

    Page<ProductResDTO> getPage(ProductPageReqDTO productShowReqDTO, Pageable pageable);

}
