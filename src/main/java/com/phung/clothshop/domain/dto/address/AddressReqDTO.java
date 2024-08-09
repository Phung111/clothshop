package com.phung.clothshop.domain.dto.address;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import com.phung.clothshop.domain.entity.customer.Address;
import com.phung.clothshop.domain.entity.customer.Customer;
import com.phung.clothshop.domain.entity.ship.EPronvince;
import com.phung.clothshop.utils.customAnnotation.EnumValidCheck;
import com.phung.clothshop.utils.customAnnotation.EnumValidCheckName;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AddressReqDTO {

    @NotBlank(message = "nameCustomer can not blank")
    private String nameCustomer;

    @NotBlank(message = "phone can not blank")
    @Pattern(regexp = "^0[1-9][0-9]{8}$", message = "Phone is invalid!")
    private String phone;

    @NotBlank(message = "province can not blank")
    @EnumValidCheckName(enumClass = EPronvince.class, message = "Invalid pronvice value")
    private String pronvice;

    @NotBlank(message = "address can not blank")
    private String address;

    private Boolean isDefault = false;

    public Address toAddress(Customer customer) {

        return new Address()
                .setNameCustomer(nameCustomer)
                .setPhone(phone)
                .setEPronvince(EPronvince.fromName(pronvice))
                .setAddress(address)
                .setCustomer(customer)
                .setIsDefault(isDefault);
    }

}
