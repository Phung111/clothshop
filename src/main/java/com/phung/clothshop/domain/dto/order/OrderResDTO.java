package com.phung.clothshop.domain.dto.order;

import java.util.List;

import com.phung.clothshop.domain.dto.address.AddressResDTO;
import com.phung.clothshop.domain.dto.cusomter.CustomerOrderResDTO;
import com.phung.clothshop.domain.dto.voucher.VoucherResDTO;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderResDTO {

    private Long id;

    // private Long customerID;

    private CustomerOrderResDTO customer;

    private List<OrderItemResDTO> orderItems;

    private VoucherResDTO voucher;

    private AddressResDTO address;

    private Long orderItemsTotal;

    private Long shipTotal;

    private Long voucherTotal;

    private Long total;
}
