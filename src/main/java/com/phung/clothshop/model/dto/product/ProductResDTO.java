package com.phung.clothshop.model.dto.product;

import lombok.*;

import java.util.Date;
import java.util.List;

import com.phung.clothshop.model.product.ECategory;
import com.phung.clothshop.model.product.EColor;
import com.phung.clothshop.model.product.EProductStatus;
import com.phung.clothshop.model.product.ESize;
import com.phung.clothshop.model.productDetail.ProductDetail;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductResDTO {

    private Long id;
    private String name;
    private Long price;
    private Long quantity;
    private Long sold;
    private String decription;
    private ECategory eCategory;
    private EColor eColor;
    private ESize eSize;
    private EProductStatus eProductStatus;
    private ProductDetail productDetail;
    private List<ProductImageDTO> images;
    private Boolean deleted;
    private Date createdAt;
    private Date updatedAt;

}
