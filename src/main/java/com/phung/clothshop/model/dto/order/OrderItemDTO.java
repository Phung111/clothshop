package com.phung.clothshop.model.dto.order;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderItemDTO {

    private Long orderItemID;

    private Long orderID;

    private Long productID;

    private String name;

    private Long price;

    private String variation;

    private Long quantity;

    private Long total;
}
