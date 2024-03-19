package com.phung.clothshop.domain.dto.discount;

import java.text.ParseException;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import com.phung.clothshop.domain.entity.product.Discount;
import com.phung.clothshop.utils.DateFormat;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DiscountCreateReqDTO {

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

    @NotBlank(message = "productIDs can not blank")
    private List<Long> productIDs;

    public Discount toDiscount() throws ParseException {
        return new Discount()
                .setDateStart(DateFormat.parse(dateStart))
                .setDateEnd(DateFormat.parse(dateEnd))
                .setPercent(Long.parseLong(percent));
    }
}
