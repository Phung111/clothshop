package com.phung.clothshop.domain.dto.account;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.phung.clothshop.domain.entity.account.Account;
import com.phung.clothshop.domain.entity.account.ERole;
import com.phung.clothshop.domain.entity.customer.Address;
import com.phung.clothshop.domain.entity.customer.Customer;
import com.phung.clothshop.domain.entity.customer.EGender;
import com.phung.clothshop.domain.entity.order.Cart;
import com.phung.clothshop.domain.entity.ship.EPronvince;
import com.phung.clothshop.utils.customAnnotation.EnumValidCheck;
import com.phung.clothshop.utils.customAnnotation.EnumValidCheckName;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AccountRegisterReqDTO {

    private Long id;

    @Pattern(regexp = "^[\\w]+@([\\w-]+\\.)+[\\w-]{2,6}$", message = "Email is invalid!")
    @Size(min = 8, max = 35, message = "Email length should be between 8 and 35 characters!")
    private String username;

    @NotBlank(message = "Please enter password!")
    @Size(min = 3, max = 50, message = "Password length should be between 3 and 50 characters!")
    private String password;

    private String eRole = ERole.USER.toString();

    @NotBlank(message = "Name can not blank!")
    @Size(min = 3, max = 30, message = "Name length must be between 3 and 30 characters!")
    private String name;

    @NotBlank(message = "Phone can not blank")
    @Pattern(regexp = "^0[1-9][0-9]{8}$", message = "Phone is invalid!")
    private String phone;

    @NotBlank(message = "Address can not blank")
    private String address;

    @NotBlank(message = "EGender can not blank")
    @EnumValidCheckName(enumClass = EGender.class, message = "Invalid gender value")
    private String gender;

    @NotBlank(message = "Chose date of birth please!")
    @Pattern(regexp = "^\\d{4}\\-(0?[1-9]|1[012])\\-(0?[1-9]|[12][0-9]|3[01])$", message = "Date of birth is invalid!.")
    private String dob;

    @NotBlank(message = "pronvice can not blank")
    @EnumValidCheckName(enumClass = EPronvince.class, message = "Invalid pronvice value")
    private String pronvice;

    public Account toAccount() {
        return new Account()
                .setId(id)
                .setUsername(username)
                .setPassword(password)
                .setERole(ERole.valueOf(eRole));
    }

    public Customer toCustomer(Account account, Cart cart) throws NumberFormatException, ParseException {

        return new Customer()
                .setName(name)
                .setEGender(EGender.fromName(gender))
                .setDob(new SimpleDateFormat("yyyy-MM-dd").parse(dob))
                .setAccount(account)
                .setCart(cart);
    }

    public Address toAddress(Customer customer) {
        return new Address()
                .setNameCustomer(customer.getName())
                .setPhone(phone)
                .setEPronvince(EPronvince.fromName(pronvice))
                .setAddress(address)
                .setCustomer(customer)
                .setIsDefault(true);
    }

}
