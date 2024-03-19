package com.phung.clothshop.domain.dto.order;

import java.util.List;

import com.phung.clothshop.domain.dto.address.AddressResDTO;
import com.phung.clothshop.domain.dto.voucher.VoucherResDTO;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BillResDTO {

    private Long billID;

    private Long customerID;

    private Long orderID;

    private AddressResDTO addressResDTO;

    private List<OrderItemDTO> orderItemDTOs;

    private VoucherResDTO voucherResDTO;

    private TotalResDTO totalResDTO;
}
