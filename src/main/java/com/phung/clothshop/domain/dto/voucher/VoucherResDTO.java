package com.phung.clothshop.domain.dto.voucher;

import java.util.Date;

import com.phung.clothshop.domain.entity.order.Voucher;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class VoucherResDTO {

    private String id;

    private String dateStart;

    private String dateEnd;

    private Long percent;

    private Long price;
}
