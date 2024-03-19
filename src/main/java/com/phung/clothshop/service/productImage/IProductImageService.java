package com.phung.clothshop.service.productImage;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.phung.clothshop.domain.entity.product.Product;
import com.phung.clothshop.domain.entity.product.ProductImage;
import com.phung.clothshop.service.IGeneralService;

public interface IProductImageService extends IGeneralService<ProductImage, Long> {

    List<ProductImage> uploadAndSaveImage(Product product, MultipartFile[] multipartFiles);

    List<ProductImage> setDefaultAndSaveImage(Product product);

    void deleteSelectImages(List<Long> idImageDeletes, List<ProductImage> productImages);

    List<ProductImage> findByProduct_Id(Long productId);
}
