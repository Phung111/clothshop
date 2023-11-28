package com.phung.clothshop.service;

import org.springframework.web.multipart.MultipartFile;

import com.phung.clothshop.model.dto.ProductImageDTO;
import com.phung.clothshop.model.product.Product;
import com.phung.clothshop.model.product.ProductImage;

public interface IProductImageService extends IGeneralService<ProductImage, Long> {

    ProductImageDTO uploadAndSaveImage(Product product, MultipartFile multipartFile);

    ProductImageDTO setDefaultAndSaveImage(Product product);

}
