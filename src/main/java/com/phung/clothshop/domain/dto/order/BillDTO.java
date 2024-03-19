package com.phung.clothshop.domain.dto.order;

import java.util.List;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BillDTO {

    private Long billID;

    private Long customerID;

    private Long orderID;

    private List<OrderItemDTO> orderitemDTOs;

    private Long orderItemsTotal;

    private Long shipTotal;

    private Long orderTotal;
}
