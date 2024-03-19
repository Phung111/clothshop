package com.phung.clothshop.service.product;

import org.springframework.data.domain.Pageable;

import java.text.ParseException;
import java.util.List;

import org.springframework.data.domain.Page;

import com.phung.clothshop.domain.dto.product.ProductCreateReqDTO;
import com.phung.clothshop.domain.dto.product.ProductPageReqDTO;
import com.phung.clothshop.domain.dto.product.ProductResDTO;
import com.phung.clothshop.domain.entity.product.Product;
import com.phung.clothshop.service.IGeneralService;

public interface IProductService extends IGeneralService<Product, Long> {

    Page<ProductResDTO> getPage(ProductPageReqDTO productShowReqDTO, Pageable pageable);

    ProductResDTO saveProductAndImage(ProductCreateReqDTO productCreateReqDTO)
            throws NumberFormatException, ParseException;

    List<Product> findByDiscountId(Long discountId);

    List<ProductResDTO> findProductsDiscount();

    List<ProductResDTO> findTopSale(Pageable pageable);
}
