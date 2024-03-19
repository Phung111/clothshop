package com.phung.clothshop.domain.dto.voucher;

import java.util.List;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class VouchersAndQuantityResDTO {

    private Long quantity;

    private List<VoucherResDTO> voucherCreatResDTOs;

}
