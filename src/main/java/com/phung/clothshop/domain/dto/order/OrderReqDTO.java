package com.phung.clothshop.domain.dto.order;

import java.util.List;

import javax.validation.constraints.NotBlank;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderReqDTO {

    @NotBlank(message = "addressID can not blank")
    private String addressID;

    private List<Long> cartItemIDs;

    private String voucherID;

}
