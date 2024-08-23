package com.phung.clothshop.domain.dto.voucher;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
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

    private String percent;

    private String price;

    public Voucher toVoucher() throws ParseException {

        long percentValue = (percent == null || percent.trim().isEmpty()) ? 0 : Long.parseLong(percent);
        long priceValue = (price == null || price.trim().isEmpty()) ? 0 : Long.parseLong(price);


        return new Voucher()
                .setDateStart(DateFormat.parse(dateStart))
                .setDateEnd(DateFormat.parse(dateEnd))
                .setPercent(percentValue)
                .setPrice(priceValue);
    }

}
