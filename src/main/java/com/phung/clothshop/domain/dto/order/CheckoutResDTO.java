package com.phung.clothshop.domain.dto.order;

import java.util.List;

import com.phung.clothshop.domain.dto.address.AddressResDTO;
import com.phung.clothshop.domain.dto.voucher.VoucherResDTO;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CheckoutResDTO {

    private AddressResDTO address;

    private List<CartItemDTO> cartItems;

    private VoucherResDTO voucher;

    private TotalResDTO total;
}
