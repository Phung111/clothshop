package com.phung.clothshop.domain.dto.account;

import com.phung.clothshop.domain.dto.cusomter.CustomerResDTO;
import com.phung.clothshop.domain.dto.order.CartDTO;
import com.phung.clothshop.domain.entity.order.Cart;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AccountResDTO {
    
    private CustomerResDTO customer;
    private CartDTO cart;
    private String jwt;


}
