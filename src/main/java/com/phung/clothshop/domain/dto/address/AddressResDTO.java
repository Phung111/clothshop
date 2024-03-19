package com.phung.clothshop.domain.dto.address;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AddressResDTO {

    private Long id;

    private String nameCustomer;

    private String phone;

    private String ePronvince;

    private String address;

    private Boolean isDefault;

}
