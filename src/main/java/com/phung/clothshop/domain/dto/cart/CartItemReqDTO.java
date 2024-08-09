package com.phung.clothshop.domain.dto.cart;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import com.phung.clothshop.domain.entity.product.EColor;
import com.phung.clothshop.domain.entity.product.ESize;

import com.phung.clothshop.utils.customAnnotation.EnumValidCheck;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CartItemReqDTO {

    @NotBlank(message = "idProduct can not blank")
    private String idProduct;

    @NotBlank(message = "nameCustomer can not blank")
    @EnumValidCheck(enumClass = EColor.class, message = "Invalid ePronvince value")
    private String color;

    @NotBlank(message = "nameCustomer can not blank")
    @EnumValidCheck(enumClass = ESize.class, message = "Invalid ePronvince value")
    private String size;

    @NotBlank(message = "quantity can not blank")
    @Pattern(regexp = "^(0|[1-9][0-9]*)$", message = "quantity product is not valid number")
    @Pattern(regexp = "^[1-9][0-9]*$", message = "quantity must be at least 1")
    private String quantity;
    
}
