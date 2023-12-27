package com.phung.clothshop.model.dto.order;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CartItemDTO {

    private Long cartItemId;

    private Long cartId;

    private Long productId;

    private String name;

    private Long price;

    private String variation;

    private Long quantity;

    private Long total;

}
