package com.phung.clothshop.domain.dto.order;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TotalResDTO {

    private Long itemsTotal;

    private Long shipTotal;

    private Long voucherTotal;

    private Long grandTotal;

    public void caculateGrandTotal() {

        if (itemsTotal == null) {
            itemsTotal = 0L;
        }
        if (shipTotal == null) {
            shipTotal = 0L;
        }
        if (voucherTotal == null) {
            voucherTotal = 0L;
        }
        grandTotal = itemsTotal + shipTotal - voucherTotal;
    }

}
