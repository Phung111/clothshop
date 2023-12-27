package com.phung.clothshop.model.dto.order;

import java.util.List;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderDTO {

    private Long orderID;

    private Long customerID;

    private Long total;

    private List<OrderItemDTO> orderitemDTOs;

    private Long shipTotal;
}
