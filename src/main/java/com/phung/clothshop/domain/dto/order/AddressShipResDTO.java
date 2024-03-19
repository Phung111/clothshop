package com.phung.clothshop.domain.dto.order;

import com.phung.clothshop.domain.dto.address.AddressResDTO;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AddressShipResDTO {

    private AddressResDTO addressChangeResDTO;

    private Long shipTotalChangeRes;
}
