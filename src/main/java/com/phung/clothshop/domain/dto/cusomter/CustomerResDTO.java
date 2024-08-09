package com.phung.clothshop.domain.dto.cusomter;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CustomerResDTO {

    private Long customerId;

    private String email;
    private String role;

    private String name;
    private String gender;
    private String dob;
}
