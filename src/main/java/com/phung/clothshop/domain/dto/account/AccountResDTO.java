package com.phung.clothshop.domain.dto.account;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AccountResDTO {

    private Long id;
    private String username;
    private String password;
    private String eRole;

}
