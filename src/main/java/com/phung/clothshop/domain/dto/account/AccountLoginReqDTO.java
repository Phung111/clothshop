package com.phung.clothshop.domain.dto.account;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AccountLoginReqDTO {

    @Pattern(regexp = "^[\\w]+@([\\w-]+\\.)+[\\w-]{2,6}$", message = "Email is invalid!")
    @Size(min = 8, max = 35, message = "Email length should be between 8 and 35 characters!")
    private String username;

    @NotBlank(message = "Please enter password!")
    @Size(min = 3, max = 50, message = "Password length should be between 3 and 50 characters!")
    private String password;

}
