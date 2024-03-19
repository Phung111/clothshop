package com.phung.clothshop.domain.dto.order;

import java.util.List;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderDTO {

    private Long orderID;

    private Long customerID;

    private List<OrderItemDTO> orderitemDTOs;

    private Long orderitemsTotal;

    private Long shipTotal;

    private Long orderTotal;

}
