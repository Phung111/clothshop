package com.phung.clothshop.domain.dto.order;

import com.phung.clothshop.domain.dto.product.ProductResDTO;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderItemResDTO {

    private Long id;

    private Long OrderID;

    private ProductResDTO product;

    private String variation;

    private Long quantity;

    private Long total;
    
}
