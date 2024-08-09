package com.phung.clothshop.domain.dto.order;

import com.phung.clothshop.domain.dto.discount.DiscountResDTO;
import com.phung.clothshop.domain.dto.product.ProductImageDTO;
import com.phung.clothshop.domain.entity.product.ECategory;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CartItemDTO {

    private Long cartItemId;

    private Long cartId;

    private Long productId;

    private ECategory category;

    private ProductImageDTO image;

    private String name;

    private Long price;

    private Long priceTotal;

    private DiscountResDTO discount;

    private String variation;

    private Long quantity;

    private Long total;

}
