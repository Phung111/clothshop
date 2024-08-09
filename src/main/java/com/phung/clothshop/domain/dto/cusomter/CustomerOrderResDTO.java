package com.phung.clothshop.domain.dto.cusomter;

import java.util.List;

import com.phung.clothshop.domain.dto.address.AddressResDTO;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CustomerOrderResDTO {
    

    private Long id;

    private String name;

    private String role;

}
