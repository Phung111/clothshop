package com.phung.clothshop.domain.dto.voucher;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.springframework.beans.factory.annotation.Autowired;

import com.phung.clothshop.domain.entity.order.Voucher;
import com.phung.clothshop.utils.DateFormat;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class VoucherCreateReqDTO {

    @NotBlank(message = "quantity can not blank")
    @Pattern(regexp = "^(0|[1-9][0-9]*)$", message = "quantity product is not valid number")
    @Pattern(regexp = "^(?:[1-9]|[1-9]\\d{1,2}|1000)$", message = "quantity must be between 1 and 1.000")
    private String quantity;

    @NotBlank(message = "dateStart can not blank")
    private String dateStart;

    @NotBlank(message = "dateEnd can not blank")
    private String dateEnd;

    @NotBlank(message = "percent can not blank")
    @Pattern(regexp = "^(0|[1-9][0-9]*)$", message = "percent is not valid number")
    @Pattern(regexp = "^(100|[1-9]?[0-9])$", message = "percent must be between 0 and 100")
    private String percent;

    @NotBlank(message = "price can not blank")
    @Pattern(regexp = "^(0|[1-9][0-9]*)$", message = "price product is not valid number")
    @Pattern(regexp = "^(1000|[1-9]\\d{3,6})$", message = "price must be between 1.000 and 10.000.000")
    private String price;

    public Voucher toVoucher() throws ParseException {

        return new Voucher()
                .setDateStart(DateFormat.parse(dateStart))
                .setDateEnd(DateFormat.parse(dateEnd))
                .setPercent(Long.valueOf(percent))
                .setPrice(Long.valueOf(price));
    }

}
