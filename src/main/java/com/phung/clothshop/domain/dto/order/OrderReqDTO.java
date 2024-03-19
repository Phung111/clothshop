package com.phung.clothshop.domain.dto.order;

import java.util.List;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderReqDTO {

    private String voucherID;

    private List<Long> cartItemIDs;
}
