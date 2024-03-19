package com.phung.clothshop.domain.dto.order;

import java.util.List;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CartDTO {

    private Long cartId;

    private List<CartItemDTO> cartItemDTOs;

}
